package dev.bogdanzurac.marp.feature.crypto.ui

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.getStringArg
import dev.bogdanzurac.marp.core.navigation.navigation
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoDetails.CRYPTO_ID

fun NavGraphBuilder.cryptoNavGraph() {
    navigation(
        startDestination = CryptoList,
        route = Crypto
    ) {
        composable(CryptoList) { CryptoListScreen() }
        composable(CryptoDetails) { CryptoDetailsScreen(it.getStringArg(CRYPTO_ID)!!) }
    }
}