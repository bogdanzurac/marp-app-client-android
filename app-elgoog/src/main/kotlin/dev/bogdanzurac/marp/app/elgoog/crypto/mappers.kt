package dev.bogdanzurac.marp.app.elgoog.crypto

fun CryptoAssetModel.toEntity(): CryptoAssetEntity =
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

fun CryptoAssetEntity.toModel(): CryptoAssetModel =
    CryptoAssetModel(id, rank, symbol, name, priceUsd, changePercent, marketCapUsd, supply, maxSupply)