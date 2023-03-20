package dev.bogdanzurac.marp.app.elgoog.crypto

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.bogdanzurac.marp.app.elgoog.core.navigation.stringArg
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoRoute.AssetDetails.Companion.CRYPTO_ID_ARG

fun NavGraphBuilder.cryptoNavGraph() {
    navigation(
        startDestination = CryptoRoute.AssetsList.path,
        route = CryptoRoute.Root.path
    ) {
        composable(CryptoRoute.AssetsList.path) { CryptoListScreen() }
        composable(
            CryptoRoute.AssetDetails().path,
            listOf(stringArg(CRYPTO_ID_ARG))
        ) { CryptoDetailsScreen(it.arguments?.getString(CRYPTO_ID_ARG)!!) }
    }
}