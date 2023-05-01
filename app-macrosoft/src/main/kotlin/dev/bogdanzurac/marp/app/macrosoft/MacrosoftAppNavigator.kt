package dev.bogdanzurac.marp.app.macrosoft

import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoNavigator
import dev.bogdanzurac.marp.feature.news.ui.News
import dev.bogdanzurac.marp.feature.news.ui.NewsNavigator
import dev.bogdanzurac.marp.feature.weather.ui.Weather
import dev.bogdanzurac.marp.feature.weather.ui.WeatherNavigator
import org.koin.core.annotation.Single

@Single
class MacrosoftAppNavigator(
    private val cryptoNavigator: CryptoNavigator,
    private val newsNavigator: NewsNavigator,
    private val weatherNavigator: WeatherNavigator,
) : AppNavigator() {

    override fun getFeatureNavigatorForRoute(route: String): FeatureNavigator {
        logger.d("Getting FeatureNavigator for route: $route")
        return when (route) {
            Crypto.path -> cryptoNavigator
            News.path -> newsNavigator
            Weather.path -> weatherNavigator
            else -> throw IllegalStateException("Current route '$route' is not associated with a FeatureNavigator")
        }
    }
}