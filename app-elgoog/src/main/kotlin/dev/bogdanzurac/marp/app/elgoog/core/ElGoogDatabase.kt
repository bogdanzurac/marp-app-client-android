package dev.bogdanzurac.marp.app.elgoog.core

import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoAssetEntity
import dev.bogdanzurac.marp.app.elgoog.news.NewsArticleEntity
import dev.bogdanzurac.marp.core.db.BaseDatabase
import io.realm.kotlin.types.BaseRealmObject
import org.koin.core.annotation.Single
import kotlin.reflect.KClass

@Single
class ElGoogDatabase : BaseDatabase() {

    override val entities: Set<KClass<out BaseRealmObject>> = setOf(
        CryptoAssetEntity::class,
        NewsArticleEntity::class
    )
}