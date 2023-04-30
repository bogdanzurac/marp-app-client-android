package dev.bogdanzurac.marp.feature.crypto.data.dao

import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset

internal fun CryptoAsset.toEntity(): CryptoAssetEntity =
    CryptoAssetEntity().also {
        it.id = id
        it.rank = rank
        it.symbol = symbol
        it.name = name
        it.priceUsd = priceUsd
        it.changePercent = changePercent
        it.marketCapUsd = marketCapUsd
        it.supply = supply
        it.maxSupply = maxSupply
    }

internal fun CryptoAssetEntity.toCryptoAsset(): CryptoAsset =
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