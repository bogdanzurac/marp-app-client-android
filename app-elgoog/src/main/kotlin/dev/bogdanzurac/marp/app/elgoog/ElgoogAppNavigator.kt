package dev.bogdanzurac.marp.app.elgoog

import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoNavigator
import dev.bogdanzurac.marp.feature.auth.ui.Auth
import dev.bogdanzurac.marp.feature.auth.ui.AuthNavigator
import dev.bogdanzurac.marp.feature.movies.ui.Movies
import dev.bogdanzurac.marp.feature.movies.ui.MoviesNavigator
import dev.bogdanzurac.marp.feature.news.ui.News
import dev.bogdanzurac.marp.feature.news.ui.NewsNavigator
import dev.bogdanzurac.marp.feature.notes.ui.Notes
import dev.bogdanzurac.marp.feature.notes.ui.NotesNavigator
import dev.bogdanzurac.marp.feature.weather.ui.Weather
import dev.bogdanzurac.marp.feature.weather.ui.WeatherNavigator
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class ElgoogAppNavigator(
    private val authNavigator: AuthNavigator,
    private val cryptoNavigator: CryptoNavigator,
    private val moviesNavigator: MoviesNavigator,
    private val newsNavigator: NewsNavigator,
    private val notesNavigator: NotesNavigator,
    private val weatherNavigator: WeatherNavigator,
) : AppNavigator() {

    override fun getFeatureNavigatorForRoute(route: String): FeatureNavigator {
        logger.d("Getting FeatureNavigator for route: $route")
        return when (route) {
            Auth.path -> authNavigator
            Crypto.path -> cryptoNavigator
            Movies.path -> moviesNavigator
            News.path -> newsNavigator
            Notes.path -> notesNavigator
            Weather.path -> weatherNavigator
            else -> throw IllegalStateException("Current route '$route' is not associated with a FeatureNavigator")
        }
    }
}