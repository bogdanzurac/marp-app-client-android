package dev.bogdanzurac.marp.lib.services.google

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val libServicesGoogleModule = module {

    singleOf(::GoogleLocationProvider)
}
