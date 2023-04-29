package dev.bogdanzurac.marp.lib.services.huawei

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val libServicesHuaweiModule = module {

    singleOf(::HuaweiLocationProvider)
}
