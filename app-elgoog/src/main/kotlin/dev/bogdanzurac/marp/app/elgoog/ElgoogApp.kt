package dev.bogdanzurac.marp.app.elgoog

import CRYPTO_API_KEY
import WEATHER_API_KEY
import android.app.Application
import dev.bogdanzurac.marp.feature.crypto.data.web.CRYPTO_KEY
import dev.bogdanzurac.marp.feature.weather.data.WEATHER_KEY
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
                    CRYPTO_KEY to CRYPTO_API_KEY,
                    WEATHER_KEY to WEATHER_API_KEY,
                )
            )
        }
    }
}
