package dev.bogdanzurac.marp.app.elgoog.login

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.navigation

fun NavGraphBuilder.loginNavGraph() {
    navigation(
        startDestination = Login,
        route = Auth
    ) {
        composable(Login) { LoginScreen() }
        composable(Signup) { SignupScreen() }
    }
}
