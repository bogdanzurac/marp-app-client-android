package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
class CryptoDao : BaseDao<CryptoAssetEntity>()