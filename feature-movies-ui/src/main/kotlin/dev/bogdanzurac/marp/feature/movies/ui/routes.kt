package dev.bogdanzurac.marp.feature.movies.ui

import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.core.navigation.AppRoute.SimpleRoute
import dev.bogdanzurac.marp.core.navigation.NavArg
import dev.bogdanzurac.marp.core.navigation.longArg
import dev.bogdanzurac.marp.core.navigation.withArg

object Movies : SimpleRoute("movies")

object MoviesList : SimpleRoute("movies/list")

object MovieDetails : AppRoute.ArgsRoute() {

    internal const val MOVIE_ID = "movie_id"
    override val args: List<NavArg> = listOf(longArg(MOVIE_ID))
    override val path = "movies/list/{$MOVIE_ID}"

    operator fun invoke(id: Long? = null): AppRoute =
        SimpleRoute(id?.let { path.withArg(MOVIE_ID, it.toString()) } ?: path)
}
