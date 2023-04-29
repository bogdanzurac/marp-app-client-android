package dev.bogdanzurac.marp.core.data

import android.content.Context
import org.koin.core.annotation.Single

@Single
class SyncTimestampsPreferences(context: Context) :
    BasePreferences(context, "sync_timestamps") {

    fun getTimestampFor(key: String): Long =
        settings.getLong(key, -1)

    fun saveTimestampFor(key: String, timestamp: Long) =
        settings.putLong(key, timestamp)
}