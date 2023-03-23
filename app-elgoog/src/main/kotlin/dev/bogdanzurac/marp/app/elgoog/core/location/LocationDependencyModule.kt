package dev.bogdanzurac.marp.app.elgoog.core.location

import android.os.Build
import org.koin.dsl.module

val locationDependencyModule = module {

    single {
        if (isHuaweiDevice()) get<HuaweiLocationProvider>()
        else get<GoogleLocationProvider>()
    }
}

private fun isHuaweiDevice() = Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true)
        && !Build.MODEL.equals("Nexus 6P", ignoreCase = false)