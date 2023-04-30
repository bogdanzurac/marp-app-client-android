package dev.bogdanzurac.marp.core.navigation

typealias RoutePath = String

fun RoutePath.withArg(arg: String, value: String): RoutePath =
    this.replace("{${arg}}", value)

sealed interface AppRoute {
    val path: RoutePath

    open class SimpleRoute(override val path: RoutePath) : AppRoute {
        operator fun invoke(): RoutePath = path
    }

    abstract class ArgsRoute : AppRoute {
        abstract val args: List<NavArg>
        abstract override val path: RoutePath
    }
}
