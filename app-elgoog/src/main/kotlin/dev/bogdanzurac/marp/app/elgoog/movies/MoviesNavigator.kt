package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.app.elgoog.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class MoviesNavigator : FeatureNavigator() {

    fun navigateToMovieDetails(id: Long) = navigateTo(MovieDetails(id))
}
