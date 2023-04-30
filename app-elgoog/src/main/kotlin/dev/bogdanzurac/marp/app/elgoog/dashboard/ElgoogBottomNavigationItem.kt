package dev.bogdanzurac.marp.app.elgoog.dashboard

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.dashboard.ui.BottomNavigationItem
import dev.bogdanzurac.marp.feature.movies.ui.Movies
import dev.bogdanzurac.marp.feature.news.ui.News
import dev.bogdanzurac.marp.feature.notes.ui.Notes
import dev.bogdanzurac.marp.feature.weather.ui.Weather

internal sealed class ElgoogBottomNavigationItem(
    override val route: AppRoute,
    override val imageRes: Int,
    override val titleRes: Int,
) : BottomNavigationItem {

    object CryptoNavigationItem : ElgoogBottomNavigationItem(
        Crypto,
        R.drawable.ic_crypto,
        R.string.title_crypto
    )

    object MoviesNavigationItem : ElgoogBottomNavigationItem(
        Movies,
        R.drawable.ic_movies,
        R.string.title_movies
    )

    object NotesNavigationItem : ElgoogBottomNavigationItem(
        Notes,
        R.drawable.ic_notes,
        R.string.title_notes
    )

    object NewsNavigationItem : ElgoogBottomNavigationItem(
        News,
        R.drawable.ic_news,
        R.string.title_news
    )

    object WeatherNavigationItem : ElgoogBottomNavigationItem(
        Weather,
        R.drawable.ic_weather,
        R.string.title_weather
    )
}
