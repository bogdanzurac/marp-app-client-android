package dev.bogdanzurac.marp.app.elgoog.notes

import com.google.firebase.Timestamp
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoAssetModel
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import java.util.*

data class Note(
    var id: String = "",
    val title: String = "",
    val body: String = "",
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val cryptoId: String? = null,
    val userId: String,
    val isEditable: Boolean = false,
    val cryptoAsset: CryptoAssetModel? = null,
)

data class NoteModel(
    override var id: String = "",
    val title: String = "",
    val body: String = "",
    val cryptoId: String = "",
    val userId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
) : IdentifiableModel

interface IdentifiableModel {
    var id: String
}

fun Note.toNoteModel(): NoteModel =
    NoteModel(
        id,
        title,
        body,
        cryptoId ?: "",
        userId,
        Timestamp(Date(createdAt.toEpochMilliseconds())),
        Timestamp(Date(updatedAt.toEpochMilliseconds()))
    )

fun NoteModel.toNote(isEditable: Boolean): Note =
    Note(
        id,
        title,
        body,
        Instant.fromEpochSeconds(createdAt.seconds),
        Instant.fromEpochSeconds(createdAt.seconds),
        cryptoId,
        userId,
        isEditable
    )