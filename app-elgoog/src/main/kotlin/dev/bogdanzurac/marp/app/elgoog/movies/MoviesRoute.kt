package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute

sealed class MoviesRoute(override val path: String) : AppRoute {
    object Root : MoviesRoute("movies")
    object TrendingMovies : MoviesRoute("movies/trending")
    class MovieDetails(id: Long? = null) :
        MoviesRoute("movies/details/" + (id ?: "{$MOVIE_ID_ARG}")) {
        companion object {
            const val MOVIE_ID_ARG = "id"
        }
    }
}
