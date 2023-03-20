package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.db.AppDatabase
import dev.bogdanzurac.marp.app.elgoog.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
class CryptoDao(appDatabase: AppDatabase) : BaseDao<CryptoAssetEntity>(appDatabase)