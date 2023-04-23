package dev.bogdanzurac.marp.core.feature

import kotlinx.coroutines.flow.Flow

interface FeatureManager {

    fun isReady(): Flow<Boolean>

    /**
     * Check to see if the requested [feature] is enabled.
     */
    fun isEnabled(feature: Feature): Boolean
}