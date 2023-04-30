package dev.bogdanzurac.marp.feature.auth.ui

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class AuthNavigator(private val callbacks: AuthCallbacks) : FeatureNavigator() {

    fun navigateToSignup() = navigateTo(Signup)

    fun navigateToMain() = callbacks.getMainRouteAction().let {
        navigateTo(it.route, it.options)
    }
}
