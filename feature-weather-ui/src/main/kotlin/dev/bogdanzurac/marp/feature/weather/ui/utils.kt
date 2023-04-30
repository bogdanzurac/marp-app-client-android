package dev.bogdanzurac.marp.feature.weather.ui

fun Double.formatTemperature(): String = this.toInt().toString() + "° C"
