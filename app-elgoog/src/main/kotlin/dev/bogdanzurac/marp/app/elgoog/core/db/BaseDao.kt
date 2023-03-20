package dev.bogdanzurac.marp.app.elgoog.core.db

import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseDao<T : RealmObject>(val database: AppDatabase) {

    inline fun <reified T : RealmObject> observeAll(): Flow<List<T>> =
        database.realm.query<T>().asFlow().map { it.list }

    inline fun <reified T : RealmObject> getAll(): List<T> =
        database.realm.query<T>().find()

    suspend fun saveAll(list: List<T>) =
        database.realm.write { list.forEach { copyToRealm(it) } }

    suspend inline fun <reified T : RealmObject> deleteAll() =
        database.realm.write { delete(T::class) }
}