package dev.bogdanzurac.marp.feature.crypto.ui

import dev.bogdanzurac.marp.core.navigation.AppRoute

interface CryptoCallbacks {

    fun getAddNoteRoute(cryptoAssetId: String): AppRoute

    fun getNoteDetailsRoute(id: String): AppRoute
}