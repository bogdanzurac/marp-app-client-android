package dev.bogdanzurac.marp.core.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.BaseRealmObject
import kotlin.reflect.KClass

abstract class BaseDatabase {

    abstract val entities: Set<KClass<out BaseRealmObject>>

    val realm: Realm by lazy {
        entities
            .let { RealmConfiguration.create(it) }
            .let { Realm.open(it) }
    }
}