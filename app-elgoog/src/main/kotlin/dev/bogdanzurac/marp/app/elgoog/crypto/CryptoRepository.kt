package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.data.DataHolder
import dev.bogdanzurac.marp.app.elgoog.core.data.SyncTimestampsPreferences
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.repeatAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

@Single
class CryptoRepository(
    private val dao: CryptoDao,
    private val webService: CryptoWebService,
    syncTimestampsPreferences: SyncTimestampsPreferences,
) {

    private val cryptoHolder = DataHolder.Builder<List<CryptoAssetModel>>("crypto")
        .fetcher { webService.getCryptoAssets() }
        .cache(
            reader = {
                dao.observeAll<CryptoAssetEntity>()
                    .map { entities -> entities.map { it.toModel() } }
            },
            writer = { models -> dao.saveAll(models.map { it.toEntity() }) },
            cleaner = { dao.deleteAll<CryptoAssetEntity>() },
        )
        .timeToLive(1.hours, syncTimestampsPreferences)
        .build()

    suspend fun getCryptoAssets(refresh: Boolean = false): Result<List<CryptoAssetModel>> =
        withContext(Dispatchers.IO) {
            cryptoHolder.get(refresh)
        }

    suspend fun getCryptoAsset(id: String): Result<CryptoAssetModel> =
        withContext(Dispatchers.IO) {
            getCryptoAssets()
                .mapCatching { crypotAsset -> crypotAsset.first { it.id == id } }
        }

    fun observeCryptoAssets(): Flow<Result<List<CryptoAssetModel>>> =
        merge(
            flowOf { cryptoHolder.get() },
            pollingFlow(),
        )
            .flowOn(Dispatchers.IO)
            .buffer(RENDEZVOUS, BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()

    fun observeCryptoAsset(id: String): Flow<Result<CryptoAssetModel>> =
        observeCryptoAssets()
            .map { cryptoAssets ->
                cryptoAssets.map { list -> list.first { it.id == id } }
            }

    private fun pollingFlow(): Flow<Result<List<CryptoAssetModel>>> =
        repeatAsFlow(5.seconds) {
            cryptoHolder.get(forceRefresh = true)
        }
}
