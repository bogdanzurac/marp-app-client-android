package dev.bogdanzurac.marp.feature.crypto.data.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

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