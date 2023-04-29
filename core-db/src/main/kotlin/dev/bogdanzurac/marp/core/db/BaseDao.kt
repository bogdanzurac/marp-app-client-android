package dev.bogdanzurac.marp.core.db

import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseDao<T : RealmObject> : KoinComponent {

    val database: BaseDatabase by inject()

    inline fun <reified T : RealmObject> observeAll(): Flow<List<T>> =
            database.realm.query<T>().asFlow().map { it.list }

    inline fun <reified T : RealmObject> getAll(): List<T> =
            database.realm.query<T>().find()

    suspend fun saveAll(list: List<T>) =
            database.realm.write { list.forEach { copyToRealm(it) } }

    suspend inline fun <reified T : RealmObject> deleteAll() =
            database.realm.write { delete(T::class) }
}