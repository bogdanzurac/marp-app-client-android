package dev.bogdanzurac.marp.feature.movies.ui

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.getLongArg
import dev.bogdanzurac.marp.core.navigation.navigation
import dev.bogdanzurac.marp.feature.movies.ui.MovieDetails.MOVIE_ID

fun NavGraphBuilder.moviesNavGraph() {
    navigation(
        startDestination = MoviesList,
        route = Movies
    ) {
        composable(MoviesList) { MoviesListScreen() }
        composable(MovieDetails) { MovieDetailsScreen(it.getLongArg(MOVIE_ID)!!) }
    }
}