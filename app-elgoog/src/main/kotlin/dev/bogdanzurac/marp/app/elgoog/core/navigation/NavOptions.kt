package dev.bogdanzurac.marp.app.elgoog.core.navigation

data class NavOptions(
    val singleTop: Boolean = false,
    val restoreState: Boolean = false,
    val popUpToRoute: String?,
    val popUpToInclusive: Boolean = false,
    val popUpToShouldSaveState: Boolean = false,
)