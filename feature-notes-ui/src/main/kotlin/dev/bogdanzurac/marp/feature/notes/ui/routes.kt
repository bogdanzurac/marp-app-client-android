package dev.bogdanzurac.marp.feature.notes.ui

import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.core.navigation.AppRoute.ArgsRoute
import dev.bogdanzurac.marp.core.navigation.AppRoute.SimpleRoute
import dev.bogdanzurac.marp.core.navigation.NavArg
import dev.bogdanzurac.marp.core.navigation.stringArg
import dev.bogdanzurac.marp.core.navigation.withArg

object Notes : SimpleRoute("notes")

object NotesList : SimpleRoute("notes/list")

object NoteDetails : ArgsRoute() {

    internal const val NOTE_ID = "note_id"
    override val args: List<NavArg> = listOf(stringArg(NOTE_ID))
    override val path = "notes/list/{$NOTE_ID}"

    operator fun invoke(id: String? = null): AppRoute =
        SimpleRoute(id?.let { path.withArg(NOTE_ID, it) } ?: path)
}

object AddNote : ArgsRoute() {

    internal const val CRYPTO_ID = "crypto_id"
    override val args: List<NavArg> = listOf(stringArg(CRYPTO_ID, true))
    override val path = "notes/new?$CRYPTO_ID={$CRYPTO_ID}"

    operator fun invoke(cryptoAssetId: String? = null): AppRoute =
        SimpleRoute(cryptoAssetId?.let { path.withArg(CRYPTO_ID, it) } ?: path)
}
