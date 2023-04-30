package dev.bogdanzurac.marp.feature.crypto.ui

import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.core.navigation.AppRoute.SimpleRoute
import dev.bogdanzurac.marp.core.navigation.NavArg
import dev.bogdanzurac.marp.core.navigation.stringArg
import dev.bogdanzurac.marp.core.navigation.withArg

object Crypto : SimpleRoute("crypto")

object CryptoList : SimpleRoute("crypto/list")

object CryptoDetails : AppRoute.ArgsRoute() {

    internal const val CRYPTO_ID = "crypto_id"
    override val args: List<NavArg> = listOf(stringArg(CRYPTO_ID))
    override val path = "crypto/list/{$CRYPTO_ID}"

    operator fun invoke(id: String? = null): AppRoute =
        SimpleRoute(id?.let { path.withArg(CRYPTO_ID, it) } ?: path)
}
