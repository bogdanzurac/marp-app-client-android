package dev.bogdanzurac.marp.feature.dashboard.ui

import dev.bogdanzurac.marp.core.navigation.AppRoute

interface BottomNavigationItem {
    val route: AppRoute
    val imageRes: Int
    val titleRes: Int
}