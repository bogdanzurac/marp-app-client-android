package dev.bogdanzurac.marp.app.elgoog

import WEATHER_API_KEY
import android.app.Application
import dev.bogdanzurac.marp.feature.weather.data.WeatherWebService.Companion.WEATHER_KEY
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ElgoogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(koinModules)
            properties(
                mapOf(
                    WEATHER_KEY to WEATHER_API_KEY,
                )
            )
        }
    }
}
