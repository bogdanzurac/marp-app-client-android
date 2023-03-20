package dev.bogdanzurac.marp.app.elgoog.movies

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.bogdanzurac.marp.app.elgoog.core.navigation.longArg
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesRoute.MovieDetails.Companion.MOVIE_ID_ARG

fun NavGraphBuilder.moviesNavGraph() {
    navigation(
        startDestination = MoviesRoute.TrendingMovies.path,
        route = MoviesRoute.Root.path
    ) {
        composable(MoviesRoute.TrendingMovies.path) { MoviesListScreen() }
        composable(
            MoviesRoute.MovieDetails().path,
            listOf(longArg(MOVIE_ID_ARG))
        ) { MovieDetailsScreen(it.arguments?.getLong(MOVIE_ID_ARG)!!) }
    }
}