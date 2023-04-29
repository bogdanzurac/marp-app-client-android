package dev.bogdanzurac.marp.app.elgoog

import android.app.Application
import dev.bogdanzurac.marp.app.elgoog.core.location.locationDependencyModule
import dev.bogdanzurac.marp.core.auth.CoreAuthModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class ElgoogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                ElGoogAppModule().module,
                CoreAuthModule().module,
                locationDependencyModule,
            )
        }
    }
}

@Module
@ComponentScan
class ElGoogAppModule
