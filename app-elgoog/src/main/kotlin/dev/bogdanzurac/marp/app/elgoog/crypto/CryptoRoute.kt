package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute

sealed class CryptoRoute(override val path: String) : AppRoute {
    object Root : CryptoRoute("crypto")
    object AssetsList : CryptoRoute("crypto/list")
    class AssetDetails(id: String? = null) :
        CryptoRoute("crypto/list/" + (id ?: "{$CRYPTO_ID_ARG}")) {
        companion object {
            const val CRYPTO_ID_ARG = "id"
        }
    }
}
