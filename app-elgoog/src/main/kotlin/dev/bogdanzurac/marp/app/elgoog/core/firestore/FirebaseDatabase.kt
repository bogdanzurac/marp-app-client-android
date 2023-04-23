package dev.bogdanzurac.marp.app.elgoog.core.firestore

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.bogdanzurac.marp.core.exception.DataLoadingException
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.app.elgoog.notes.IdentifiableModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Single
class FirebaseDatabase {

    val firestore = Firebase.firestore

    inline fun <reified T : IdentifiableModel> observeData(
        collectionName: String,
        filtering: List<Triple<String, Boolean, String>> = emptyList()
    ): Flow<Result<List<T>>> =
        callbackFlow {
            firestore.collection(collectionName)
                .let { reference ->
                    var mutableReference: Query = reference
                    filtering.forEach {
                        mutableReference = if (it.second)
                            mutableReference.whereEqualTo(it.first, it.third)
                        else
                            mutableReference.whereNotEqualTo(it.first, it.third)
                    }
                    mutableReference
                }
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        value.documents
                            .map { document ->
                                document.toObject(T::class.java)!!
                                    .also { it.id = document.id }
                            }
                            .also { logger.d("Received data from Firestore: $it") }
                            .let { trySend(success(it)) }
                    } else if (error != null) {
                        logger.e("Error getting data from Firestore", error)
                        if (error.code != FirebaseFirestoreException.Code.PERMISSION_DENIED)
                            trySend(failure(DataLoadingException(error)))
                    }
                }
            awaitClose {}
        }
            .distinctUntilChanged()
            .onEach {
                // delay each emission by 1s, as Firestore is very eager
                // to send back data instantly after deleting an item, thus breaking our flows
                delay(1000)
            }

    suspend inline fun <reified T : IdentifiableModel> addData(
        collectionName: String,
        data: T
    ): Result<T> =
        runCatching {
            val result = firestore.collection(collectionName).add(data).await()
            data.id = result.id
            logger.d("Added data to Firestore: $data")
            data
        }

    suspend fun <T : Any> editData(collectionName: String, id: String, data: T): Result<Unit> =
        runCatching {
            firestore.collection(collectionName).document(id).set(data).await()
            logger.d("Edited data in Firestore: $data")
        }

    suspend fun deleteData(collectionName: String, id: String): Result<Unit> =
        runCatching {
            firestore.collection(collectionName).document(id).delete().await()
            logger.d("Deleted data in Firestore with id: $id")
        }
}