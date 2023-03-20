package dev.bogdanzurac.marp.app.elgoog.core.db

import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoAssetEntity
import dev.bogdanzurac.marp.app.elgoog.news.NewsArticleEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.annotation.Single

@Single
class AppDatabase {

    val realm: Realm =
        RealmConfiguration.create(
            setOf(
                CryptoAssetEntity::class,
                NewsArticleEntity::class
            )
        ).let { Realm.open(it) }
}