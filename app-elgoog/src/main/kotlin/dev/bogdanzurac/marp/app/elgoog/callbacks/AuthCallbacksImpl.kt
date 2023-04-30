package dev.bogdanzurac.marp.app.elgoog.callbacks

import dev.bogdanzurac.marp.core.navigation.NavOptions
import dev.bogdanzurac.marp.core.navigation.NavigationAction
import dev.bogdanzurac.marp.feature.auth.ui.AuthCallbacks
import dev.bogdanzurac.marp.feature.notes.ui.Notes
import org.koin.core.annotation.Single

@Single
class AuthCallbacksImpl : AuthCallbacks {

    override fun getMainRouteAction() =
        NavigationAction(Notes, NavOptions(popUpToRoute = Notes.path))
}