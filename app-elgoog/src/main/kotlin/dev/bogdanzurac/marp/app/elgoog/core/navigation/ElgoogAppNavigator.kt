package dev.bogdanzurac.marp.app.elgoog.core.navigation

import dev.bogdanzurac.marp.app.elgoog.core.logger
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoNavigator
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoRoute
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesNavigator
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesRoute
import dev.bogdanzurac.marp.app.elgoog.news.NewsNavigator
import dev.bogdanzurac.marp.app.elgoog.news.NewsRoute
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherNavigator
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherRoute
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
            CryptoRoute.Root.path -> cryptoNavigator
            MoviesRoute.Root.path -> moviesNavigator
            NewsRoute.Root.path -> newsNavigator
            WeatherRoute.Root.path -> weatherNavigator
            else -> throw IllegalStateException("Current route '$route' is not associated with a FeatureNavigator")
        }
    }
}