package dev.bogdanzurac.marp.feature.crypto.data

import dev.bogdanzurac.marp.core.data.DataHolder
import dev.bogdanzurac.marp.core.data.SyncTimestampsPreferences
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.repeatAsFlow
import dev.bogdanzurac.marp.feature.crypto.data.dao.CryptoAssetEntity
import dev.bogdanzurac.marp.feature.crypto.data.dao.CryptoDao
import dev.bogdanzurac.marp.feature.crypto.data.dao.toCryptoAsset
import dev.bogdanzurac.marp.feature.crypto.data.dao.toEntity
import dev.bogdanzurac.marp.feature.crypto.data.web.CryptoWebService
import dev.bogdanzurac.marp.feature.crypto.data.web.toCryptoAsset
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

@Single
internal class CryptoRepositoryImpl(
    private val dao: CryptoDao,
    private val webService: CryptoWebService,
    syncTimestampsPreferences: SyncTimestampsPreferences,
) : CryptoRepository {

    private val cryptoHolder = DataHolder.Builder<List<CryptoAsset>>("crypto")
        .fetcher {
            webService.getCryptoAssets()
                .map { cryptoAssetModels -> cryptoAssetModels.map { it.toCryptoAsset() } }
        }
        .cache(
            reader = {
                dao.observeAll<CryptoAssetEntity>()
                    .map { entities -> entities.map { it.toCryptoAsset() } }
            },
            writer = { models -> dao.saveAll(models.map { it.toEntity() }) },
            cleaner = { dao.deleteAll<CryptoAssetEntity>() },
        )
        .timeToLive(1.hours, syncTimestampsPreferences)
        .build()

    override suspend fun getCryptoAssets(refresh: Boolean): Result<List<CryptoAsset>> =
        withContext(Dispatchers.IO) {
            cryptoHolder.get(refresh)
        }

    override suspend fun getCryptoAsset(id: String): Result<CryptoAsset> =
        withContext(Dispatchers.IO) {
            getCryptoAssets()
                .mapCatching { crypotAsset -> crypotAsset.first { it.id == id } }
        }

    override fun observeCryptoAssets(): Flow<Result<List<CryptoAsset>>> =
        merge(
            flowOf { cryptoHolder.get() },
            pollingFlow(),
        )
            .flowOn(Dispatchers.IO)
            .buffer(RENDEZVOUS, BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()

    override fun observeCryptoAsset(id: String): Flow<Result<CryptoAsset>> =
        observeCryptoAssets()
            .map { cryptoAssets ->
                cryptoAssets.map { list -> list.first { it.id == id } }
            }

    private fun pollingFlow(): Flow<Result<List<CryptoAsset>>> =
        repeatAsFlow(5.seconds) {
            cryptoHolder.get(forceRefresh = true)
        }
}
