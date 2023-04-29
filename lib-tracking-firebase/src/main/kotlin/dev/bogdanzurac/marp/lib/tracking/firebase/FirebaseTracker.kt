package dev.bogdanzurac.marp.lib.tracking.firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.ui.Tracker
import org.koin.core.annotation.Single

@Single
internal class FirebaseTracker : Tracker {

    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun trackScreen(screenName: String, itemId: String?) {
        logger.d("Tracking screen: $screenName")
        firebaseAnalytics.logEvent(SCREEN_VIEW) {
            param(SCREEN_NAME, screenName)
            param(SCREEN_CLASS, screenName)
            itemId?.let { param(ITEM_ID, it) }
        }
    }
}
