package dev.bogdanzurac.marp.app.macrosoft.callbacks

import dev.bogdanzurac.marp.core.navigation.BackRoute
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoCallbacks
import org.koin.core.annotation.Single

@Single
class CryptoCallbacksImpl : CryptoCallbacks {

    override fun getAddNoteRoute(cryptoAssetId: String) = BackRoute

    override fun getNoteDetailsRoute(id: String) = BackRoute
}