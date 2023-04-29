package dev.bogdanzurac.marp.core.data

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

open class BasePreferences(
    context: Context,
    filename: String
) {

    protected val settings: Settings = SharedPreferencesSettings.Factory(context).create(filename)
}