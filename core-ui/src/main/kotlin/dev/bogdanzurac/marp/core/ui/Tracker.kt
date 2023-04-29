package dev.bogdanzurac.marp.core.ui

interface Tracker {

    fun trackScreen(screenName: String, itemId: String? = null)
}
