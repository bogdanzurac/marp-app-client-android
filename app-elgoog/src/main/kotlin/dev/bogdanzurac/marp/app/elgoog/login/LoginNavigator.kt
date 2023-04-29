package dev.bogdanzurac.marp.app.elgoog.login

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import dev.bogdanzurac.marp.core.navigation.NavOptions
import dev.bogdanzurac.marp.app.elgoog.notes.Notes
import org.koin.core.annotation.Single

@Single
class LoginNavigator : FeatureNavigator() {

    fun navigateToSignup() = navigateTo(Signup)

    fun navigateToMain() = navigateTo(Notes, NavOptions(popUpToRoute = Notes.path))
}
