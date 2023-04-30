package dev.bogdanzurac.marp.lib.flagging.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.bogdanzurac.marp.core.feature.Feature
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.feature.KeyFeature
import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import org.koin.core.annotation.Singleton
import kotlin.time.Duration.Companion.minutes

@Singleton
class FirebaseFeatureManager : FeatureManager {

    private val isReadyState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1.minutes.inWholeSeconds
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val values = remoteConfig.all.mapValues { it.value.asBoolean() }
                    logger.d("Fetched RemoteConfig feature flags: $values")
                } else {
                    logger.e(
                        "Error while fetching RemoteConfig feature flags",
                        task.exception?.cause!!
                    )
                }
                isReadyState.tryEmit(true)
            }
    }

    override fun isReady(): Flow<Boolean> = isReadyState.filter { it }

    /**
     * Check to see if the requested [feature] is enabled.
     */
    override fun isEnabled(feature: Feature): Boolean =
        if (feature is KeyFeature) remoteConfig.getBoolean(feature.key)
        else false
}