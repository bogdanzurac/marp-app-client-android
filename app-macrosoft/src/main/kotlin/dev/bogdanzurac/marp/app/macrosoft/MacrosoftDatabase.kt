package dev.bogdanzurac.marp.app.macrosoft

import dev.bogdanzurac.marp.core.db.BaseDatabase
import dev.bogdanzurac.marp.feature.crypto.data.dao.CryptoAssetEntity
import dev.bogdanzurac.marp.feature.news.data.dao.NewsArticleEntity
import io.realm.kotlin.types.BaseRealmObject
import org.koin.core.annotation.Single
import kotlin.reflect.KClass

@Single
class MacrosoftDatabase : BaseDatabase() {

    override val entities: Set<KClass<out BaseRealmObject>> = setOf(
        CryptoAssetEntity::class,
        NewsArticleEntity::class
    )
}