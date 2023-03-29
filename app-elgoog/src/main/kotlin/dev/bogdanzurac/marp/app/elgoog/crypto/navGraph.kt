package dev.bogdanzurac.marp.app.elgoog.crypto

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.app.elgoog.core.navigation.composable
import dev.bogdanzurac.marp.app.elgoog.core.navigation.getStringArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.navigation
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoDetails.CRYPTO_ID

fun NavGraphBuilder.cryptoNavGraph() {
    navigation(
        startDestination = CryptoList,
        route = Crypto
    ) {
        composable(CryptoList) { CryptoListScreen() }
        composable(CryptoDetails) { CryptoDetailsScreen(it.getStringArg(CRYPTO_ID)!!) }
    }
}