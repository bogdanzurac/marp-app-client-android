package dev.bogdanzurac.marp.feature.crypto.data.web

import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset

internal fun CryptoAssetModel.toCryptoAsset(): CryptoAsset =
    CryptoAsset(
        id,
        rank,
        symbol,
        name,
        priceUsd,
        changePercent,
        marketCapUsd,
        supply,
        maxSupply
    )

internal fun CryptoAsset.toModel(): CryptoAssetModel =
    CryptoAssetModel(
        id,
        rank,
        symbol,
        name,
        priceUsd,
        changePercent,
        marketCapUsd,
        supply,
        maxSupply
    )