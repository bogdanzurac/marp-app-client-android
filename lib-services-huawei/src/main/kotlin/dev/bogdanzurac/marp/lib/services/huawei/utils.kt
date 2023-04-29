package dev.bogdanzurac.marp.lib.services.huawei

import android.os.Build

fun isHuaweiDevice() = Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true)
        && !Build.MODEL.equals("Nexus 6P", ignoreCase = false)