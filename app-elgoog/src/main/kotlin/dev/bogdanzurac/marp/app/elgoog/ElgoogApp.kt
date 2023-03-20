package dev.bogdanzurac.marp.app.elgoog

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

class ElgoogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(applicationContext)
            defaultModule()
            module { single { applicationContext } }
        }
    }
}