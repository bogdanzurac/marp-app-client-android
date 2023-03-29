package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class CryptoNavigator : FeatureNavigator() {

    fun navigateToCryptoDetails(id: String) = navigateTo(CryptoDetails(id))
}
