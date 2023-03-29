package dev.bogdanzurac.marp.app.elgoog.dashboard

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute
import dev.bogdanzurac.marp.app.elgoog.crypto.Crypto
import dev.bogdanzurac.marp.app.elgoog.dashboard.ElgoogBottomNavigationItem.*
import dev.bogdanzurac.marp.app.elgoog.movies.Movies
import dev.bogdanzurac.marp.app.elgoog.news.News
import dev.bogdanzurac.marp.app.elgoog.notes.Notes
import dev.bogdanzurac.marp.app.elgoog.weather.Weather

internal sealed class ElgoogBottomNavigationItem(
    val route: AppRoute,
    val imageRes: Int,
    val titleRes: Int,
) {

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

internal val bottomNavigationItems = listOf(
    CryptoNavigationItem,
    MoviesNavigationItem,
    NotesNavigationItem,
    NewsNavigationItem,
    WeatherNavigationItem
)