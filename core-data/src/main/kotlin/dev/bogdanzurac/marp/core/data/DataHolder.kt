@file:OptIn(ExperimentalStoreApi::class)

package dev.bogdanzurac.marp.core.data

import dev.bogdanzurac.marp.core.exception.AppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import org.mobilenativefoundation.store.store5.*
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class DataHolder<T : Any>(
    private val key: String,
    private val fetcher: suspend () -> Result<T>,
    private val reader: (() -> Flow<T>)? = null,
    private val writer: (suspend (T) -> Unit)? = null,
    private val cleaner: (suspend () -> Unit)? = null,
    private val timeToLive: Duration? = null,
    private val syncTimestampPreferences: SyncTimestampsPreferences? = null
) {

    //region DataHolder implementation
    /**
     * Variable used to retain the last time the [fetcher] was called for the in-memory cache.
     */
    private var lastSyncTimestamp: Long = 0

    private val storeBuilder: StoreBuilder<Any, T, T, *> =
        if (reader != null)
        // In case we need to enable caching, initialize the fetcher, reader, writer and cleaner
            StoreBuilder.from(
                Fetcher.of {
                    fetcher.invoke()
                        .getOrThrow()
                        .also { lastSyncTimestamp = System.currentTimeMillis() }
                },
                SourceOfTruth.of(
                    reader = { reader.invoke() },
                    writer = { _, input ->
                        // First clean the old data if necessary
                        cleaner?.invoke()
                        // then write the new data
                        writer?.invoke(input)
                        // lastly, save the new sync timestamp
                        syncTimestampPreferences?.saveTimestampFor(
                            key,
                            Clock.System.now().toEpochMilliseconds(),
                        )
                    },
                    deleteAll = { cleaner?.invoke() },
                ),
            )
        else
        // ... otherwise, just enable the fetcher.
            StoreBuilder.from(
                Fetcher.of {
                    fetcher.invoke()
                        .getOrThrow()
                        .also { lastSyncTimestamp = System.currentTimeMillis() }
                },
            )

    @ExperimentalTime
    private val store: Store<Any, T> =
        storeBuilder.let {
            // Also set a TTL policy if we have one.
            if (timeToLive != null && reader == null) {
                it.cachePolicy(
                    MemoryPolicy.builder<Any, Any>()
                        .setExpireAfterWrite(timeToLive)
                        .build(),
                )
            } else it
        }.build()
    //endregion

    //region Public API
    /**
     * One-time only loading of data. Returns the data or throws an exception.
     *
     * @param forceRefresh triggers a data refresh, even if cached data is available
     */
    @OptIn(ExperimentalTime::class)
    suspend fun get(forceRefresh: Boolean = false): Result<T> =
        if (isCacheExpired() || forceRefresh)
            runCatching { store.fresh(key) }
        else {
            // NOTE: Store doesn't know how to handle empty lists returned from the SourceOfTruth,
            // thus if the Room database returns an empty list, Store won't trigger the fetcher.
            runCatching {
                store.stream(StoreReadRequest.cached(key, refresh = false))
                    // Ignore loading
                    .filterNot { it is StoreReadResponse.Loading || it is StoreReadResponse.NoNewData }
                    // The Store implementation first returns data from [ResponseOrigin.Cache],
                    // then the same data from [ResponseOrigin.SourceOfTruth];
                    // We ignore the first one in order to not call the upstream twice
                    .filterNot { reader != null && it is StoreReadResponse.Data && it.origin == StoreReadResponseOrigin.Cache }
                    .first()
                    .requireData()
            }
        }

    /**
     * Continuously observe [Result] data events on a [Flow].
     *
     * The data stream gets disposed only when the downstream listener is disposed.
     * Data will be refreshed if [shouldRefresh] is true or [isCacheExpired] returns true
     *
     * @param firstFromCache indicates that the first returned [Result] should be from cache, if exists
     * @param shouldRefresh indicates if the data should be refreshed or not
     */
    @OptIn(ExperimentalTime::class)
    fun observe(
        firstFromCache: Boolean = false,
        shouldRefresh: Boolean = false
    ): Flow<Result<T>> =
        if (firstFromCache || !(shouldRefresh || isCacheExpired())) {
            // Only get cached data if first data returned should be from cache
            // or we don't need to refresh
            store.stream(StoreReadRequest.cached(key, refresh = shouldRefresh || isCacheExpired()))
                // The Store implementation first returns data from [ResponseOrigin.Cache],
                // then the same data from [ResponseOrigin.SourceOfTruth];
                // We ignore the first one in order to not call the upstream twice
                .filterNot { reader != null && it is StoreReadResponse.Data && it.origin == StoreReadResponseOrigin.Cache }
                .filterNot { it is StoreReadResponse.Loading }
                .map { it.toResult() }
        } else {
            // ... otherwise, we need fresh data
            store.stream(StoreReadRequest.fresh(key))
                .filterNot { it is StoreReadResponse.Loading }
                .map { it.toResult() }
        }

    /**
     * Clean both the in-memory cache as well as the disk cache associated with the [writer].
     * This should only be used when local changes should be performed on the data.
     * The majority of [DataHolder] use cases should still rely on the [fetcher] and [writer]
     * in order to handle data caching.
     */
    @OptIn(ExperimentalTime::class)
    suspend fun cleanAll() {
        store.clear()
    }

    /**
     * Mark the data as invalid, so that the next time the [get] or [observe] methods get called,
     * it will trigger a call to the [fetcher].
     */
    fun invalidateCache() {
        if (timeToLive != null) {
            lastSyncTimestamp = 0
            syncTimestampPreferences?.saveTimestampFor(key, 0)
        }
    }
    //endregion

    //region Utility methods
    private fun StoreReadResponse<T>.toResult(): Result<T> =
        when (this) {
            is StoreReadResponse.Data -> success(this.requireData())
            is StoreReadResponse.Error.Exception ->
                if (this.error is AppException) failure(this.error as AppException)
                else failure(DataException(this.error))
            is StoreReadResponse.Error.Message -> failure(DataException(message = this.message))
            is StoreReadResponse.NoNewData -> failure(DataException(message = "No new data"))
            else -> throw IllegalStateException("Loading state is not being handled!")
        }

    /**
     * Checks to see if the TTL is present and cache has expired.
     */
    private fun isCacheExpired(): Boolean =
        isMemoryCacheExpired() || isDiskCacheExpired()

    /**
     * Check if the in-memory TTL has expired.
     */
    private fun isMemoryCacheExpired(): Boolean =
        timeToLive != null && reader == null &&
                System.currentTimeMillis() - lastSyncTimestamp > timeToLive.inWholeMilliseconds

    /**
     * Check if the disk TTL has expired.
     */
    private fun isDiskCacheExpired(): Boolean =
        timeToLive != null && reader != null &&
                System.currentTimeMillis() - getLastSyncTime() > timeToLive.inWholeMilliseconds

    private fun getLastSyncTime(): Long =
        syncTimestampPreferences?.getTimestampFor(key) ?: 0
    //endregion

    //region DataHolder Builder
    /**
     * Builder for creating a new [DataHolder].
     */
    class Builder<T : Any>(private val key: String) {
        private lateinit var fetcher: suspend () -> Result<T>
        private var reader: (() -> Flow<T>)? = null
        private var writer: (suspend (T) -> Unit)? = null
        private var cleaner: (suspend () -> Unit)? = null
        private var timeToLive: Duration? = null
        private var syncTimestampProvider: SyncTimestampsPreferences? = null

        /**
         * Configure the fetcher from the remote source.
         *
         * NOTE: If multiple clients request fresh data at the same time from the [DataHolder],
         * it will not spawn multiple network [fetcher] requests, but aggregate them instead.
         */
        fun fetcher(fetcher: suspend () -> Result<T>): Builder<T> {
            this.fetcher = fetcher
            return this
        }

        /**
         * Enable caching for the data coming from the fetcher.
         *
         * @param reader Configure the data loader that reads data from the cache
         * @param writer Configure the writer that persists data to the cache
         * @param cleaner Configure the cleaner that deletes data from the cache
         */
        fun cache(
            reader: () -> Flow<T>,
            writer: suspend (T) -> Unit,
            cleaner: (suspend () -> Unit)? = null,
        ): Builder<T> {
            this.reader = reader
            this.writer = writer
            this.cleaner = cleaner
            return this
        }

        /**
         * Enable caching expiration policy.
         *
         * @param timeToLive time in millis in which the data is considered valid
         * @param syncTimestampPreferences The optional shared prefs provider used to save the last time
         * the data was cached inside the [key] key
         */
        fun timeToLive(
            timeToLive: Duration,
            syncTimestampPreferences: SyncTimestampsPreferences? = null
        ): Builder<T> {
            this.timeToLive = timeToLive
            this.syncTimestampProvider = syncTimestampPreferences
            return this
        }

        /**
         * Create a [DataHolder] instance with the currently configured settings.
         */
        fun build(): DataHolder<T> {
            return DataHolder(
                key,
                fetcher,
                reader,
                writer,
                cleaner,
                timeToLive,
                syncTimestampProvider,
            )
        }
    }
    //endregion
}

data class DataException(
    override val cause: Throwable? = null,
    override val message: String? = cause?.message
) : AppException()