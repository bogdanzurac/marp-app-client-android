package dev.bogdanzurac.marp.feature.movies.ui

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class MoviesNavigator : FeatureNavigator() {

    fun navigateToMovieDetails(id: Long) = navigateTo(MovieDetails(id))
}
