package dev.bogdanzurac.marp.app.elgoog.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

fun longArg(name: String): NamedNavArgument =
    navArgument(name) { type = NavType.LongType }

fun stringArg(name: String): NamedNavArgument =
    navArgument(name) { type = NavType.StringType }