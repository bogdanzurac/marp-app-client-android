package dev.bogdanzurac.marp.feature.auth.ui

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.navigation

fun NavGraphBuilder.authNavGraph() {
    navigation(
        startDestination = Login,
        route = Auth
    ) {
        composable(Login) { LoginScreen() }
        composable(Signup) { SignupScreen() }
    }
}
