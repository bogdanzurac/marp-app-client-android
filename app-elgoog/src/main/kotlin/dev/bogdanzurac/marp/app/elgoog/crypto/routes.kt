package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute
import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute.SimpleRoute
import dev.bogdanzurac.marp.app.elgoog.core.navigation.NavArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.stringArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.withArg

object Crypto : SimpleRoute("crypto")

object CryptoList : SimpleRoute("crypto/list")

object CryptoDetails : AppRoute.ArgsRoute() {

    internal const val CRYPTO_ID = "crypto_id"
    override val args: List<NavArg> = listOf(stringArg(CRYPTO_ID))
    override val path = "crypto/list/{${CRYPTO_ID}}"

    operator fun invoke(id: String? = null): AppRoute =
        SimpleRoute(id?.let { path.withArg(CRYPTO_ID, it) } ?: path)
}
