package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.core.ui.formatNumber
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoAssetsModel(
    @SerialName("data") val list: List<CryptoAssetModel>
)

@Serializable
data class CryptoAssetModel(
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
) {

    val priceFormatted: String
        get() = "$" + priceUsd.formatNumber()

    val changePercentFormatted: String
        get() = changePercent.formatNumber()
            .let { if (changePercent > 0) "+$it%" else "$it%" }

    val marketCapFormatted: String
        get() = "$" + marketCapUsd.formatNumber()

    val maxSupplyFormatted: String?
        get() = maxSupply?.formatNumber()

    val supplyFormatted: String
        get() = supply.formatNumber()
}

class CryptoAssetEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var rank: Int = 0
    var symbol: String = ""
    var name: String = ""
    var priceUsd: Double = 0.0
    var changePercent: Double = 0.0
    var supply: Double = 0.0
    var maxSupply: Double? = null
    var marketCapUsd: Double = 0.0
}