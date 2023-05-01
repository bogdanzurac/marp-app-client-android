package dev.bogdanzurac.marp.app.macrosoft.dashboard

import dev.bogdanzurac.marp.app.macrosoft.R
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.dashboard.ui.BottomNavigationItem
import dev.bogdanzurac.marp.feature.news.ui.News
import dev.bogdanzurac.marp.feature.weather.ui.Weather

internal sealed class MacrosoftBottomNavigationItem(
    override val route: AppRoute,
    override val imageRes: Int,
    override val titleRes: Int,
) : BottomNavigationItem {

    object CryptoNavigationItem : MacrosoftBottomNavigationItem(
        Crypto,
        R.drawable.ic_crypto,
        R.string.title_crypto
    )

    object NewsNavigationItem : MacrosoftBottomNavigationItem(
        News,
        R.drawable.ic_news,
        R.string.title_news
    )

    object WeatherNavigationItem : MacrosoftBottomNavigationItem(
        Weather,
        R.drawable.ic_weather,
        R.string.title_weather
    )
}
