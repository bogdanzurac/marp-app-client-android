package dev.bogdanzurac.marp.feature.crypto.ui

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class CryptoNavigator(private val callbacks: CryptoCallbacks) : FeatureNavigator() {

    fun navigateToCryptoDetails(id: String) =
        navigateTo(CryptoDetails(id))

    fun navigateToNoteDetails(id: String) =
        navigateTo(callbacks.getNoteDetailsRoute(id))

    fun navigateToAddNote(cryptoAssetId: String) =
        navigateTo(callbacks.getAddNoteRoute(cryptoAssetId))
}
