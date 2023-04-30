package dev.bogdanzurac.marp.feature.crypto.domain

import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun getCryptoAssets(refresh: Boolean = false): Result<List<CryptoAsset>>
    suspend fun getCryptoAsset(id: String): Result<CryptoAsset>
    fun observeCryptoAssets(): Flow<Result<List<CryptoAsset>>>
    fun observeCryptoAsset(id: String): Flow<Result<CryptoAsset>>
}