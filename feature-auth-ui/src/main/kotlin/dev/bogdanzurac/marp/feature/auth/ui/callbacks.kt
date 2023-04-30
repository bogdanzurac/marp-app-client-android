package dev.bogdanzurac.marp.feature.auth.ui

import dev.bogdanzurac.marp.core.navigation.NavigationAction

interface AuthCallbacks {

    fun getMainRouteAction(): NavigationAction
}
