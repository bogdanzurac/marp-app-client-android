package dev.bogdanzurac.marp.feature.crypto.domain

data class CryptoAsset(
    val id: String,
    val rank: Int,
    val symbol: String,
    val name: String,
    val priceUsd: Double,
    val changePercent: Double,
    val marketCapUsd: Double,
    val supply: Double,
    val maxSupply: Double?,
)
