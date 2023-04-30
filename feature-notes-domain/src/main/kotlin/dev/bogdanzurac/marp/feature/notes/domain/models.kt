package dev.bogdanzurac.marp.feature.notes.domain

import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant

data class Note(
    var id: String = "",
    val title: String = "",
    val body: String = "",
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val cryptoId: String? = null,
    val userId: String,
    val isEditable: Boolean = false,
    val cryptoAsset: CryptoAsset? = null,
)
