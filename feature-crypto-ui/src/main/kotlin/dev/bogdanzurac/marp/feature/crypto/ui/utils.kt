package dev.bogdanzurac.marp.feature.crypto.ui

import dev.bogdanzurac.marp.core.ui.formatNumber
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset

val CryptoAsset.changePercentFormatted: String
    get() = changePercent.formatNumber()
        .let { if (changePercent > 0) "+$it%" else "$it%" }

val CryptoAsset.marketCapFormatted: String
    get() = "$" + marketCapUsd.formatNumber()

val CryptoAsset.maxSupplyFormatted: String?
    get() = maxSupply?.formatNumber()

val CryptoAsset.supplyFormatted: String
    get() = supply.formatNumber()