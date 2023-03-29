package dev.bogdanzurac.marp.app.elgoog.core.navigation

import dev.bogdanzurac.marp.app.elgoog.core.logger
import dev.bogdanzurac.marp.app.elgoog.crypto.Crypto
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoNavigator
import dev.bogdanzurac.marp.app.elgoog.movies.Movies
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesNavigator
import dev.bogdanzurac.marp.app.elgoog.news.News
import dev.bogdanzurac.marp.app.elgoog.news.NewsNavigator
import dev.bogdanzurac.marp.app.elgoog.weather.Weather
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherNavigator
import org.koin.core.annotation.Single

@Single
class ElgoogAppNavigator(
    private val cryptoNavigator: CryptoNavigator,
    private val moviesNavigator: MoviesNavigator,
    private val newsNavigator: NewsNavigator,
    private val weatherNavigator: WeatherNavigator,
) : AppNavigator() {

    override fun getFeatureNavigatorForRoute(route: String): FeatureNavigator {
        logger.d("Getting FeatureNavigator for route: $route")
        return when (route) {
            Crypto.path -> cryptoNavigator
            Movies.path -> moviesNavigator
            News.path -> newsNavigator
            Weather.path -> weatherNavigator
            else -> throw IllegalStateException("Current route '$route' is not associated with a FeatureNavigator")
        }
    }
}