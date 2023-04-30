package dev.bogdanzurac.marp.feature.crypto.data.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CryptoAssetsModel(
    @SerialName("data") val list: List<CryptoAssetModel>
)

@Serializable
internal data class CryptoAssetModel(
    @SerialName("id")
    val id: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("name")
    val name: String,
    @SerialName("priceUsd")
    val priceUsd: Double,
    @SerialName("changePercent24Hr")
    val changePercent: Double,
    @SerialName("marketCapUsd")
    val marketCapUsd: Double,
    @SerialName("supply")
    val supply: Double,
    @SerialName("maxSupply")
    val maxSupply: Double?,
)
