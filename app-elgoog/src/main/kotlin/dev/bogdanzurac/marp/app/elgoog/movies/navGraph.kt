package dev.bogdanzurac.marp.app.elgoog.movies

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.app.elgoog.core.navigation.composable
import dev.bogdanzurac.marp.app.elgoog.core.navigation.getLongArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.navigation
import dev.bogdanzurac.marp.app.elgoog.movies.MovieDetails.MOVIE_ID

fun NavGraphBuilder.moviesNavGraph() {
    navigation(
        startDestination = MoviesList,
        route = Movies
    ) {
        composable(MoviesList) { MoviesListScreen() }
        composable(MovieDetails) { MovieDetailsScreen(it.getLongArg(MOVIE_ID)!!) }
    }
}