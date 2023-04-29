package dev.bogdanzurac.marp.core.ui

import android.icu.text.NumberFormat
import java.util.*

fun Double.formatNumber(): String =
    NumberFormat.getNumberInstance(Locale.getDefault()).format(this)