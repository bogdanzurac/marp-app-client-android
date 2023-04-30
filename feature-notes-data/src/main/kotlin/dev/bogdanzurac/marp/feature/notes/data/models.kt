package dev.bogdanzurac.marp.feature.notes.data

import com.google.firebase.Timestamp
import dev.bogdanzurac.marp.feature.notes.domain.Note
import dev.bogdanzurac.marp.lib.db.firebase.IdentifiableModel
import kotlinx.datetime.Instant
import java.util.*

internal data class NoteModel(
    override var id: String = "",
    val title: String = "",
    val body: String = "",
    val cryptoId: String = "",
    val userId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
) : IdentifiableModel


internal fun Note.toNoteModel(): NoteModel =
    NoteModel(
        id,
        title,
        body,
        cryptoId ?: "",
        userId,
        Timestamp(Date(createdAt.toEpochMilliseconds())),
        Timestamp(Date(updatedAt.toEpochMilliseconds()))
    )

internal fun NoteModel.toNote(isEditable: Boolean): Note =
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