package dev.bogdanzurac.marp.feature.crypto.data.dao

import dev.bogdanzurac.marp.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
internal class CryptoDao : BaseDao<CryptoAssetEntity>()