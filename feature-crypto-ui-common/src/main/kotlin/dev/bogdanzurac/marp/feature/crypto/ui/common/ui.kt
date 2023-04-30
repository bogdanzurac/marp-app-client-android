package dev.bogdanzurac.marp.feature.crypto.ui.common

import dev.bogdanzurac.marp.core.ui.formatNumber
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset

val CryptoAsset.priceFormatted: String
    get() = "$" + priceUsd.formatNumber()