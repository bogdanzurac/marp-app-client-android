package dev.bogdanzurac.marp.app.elgoog

import dev.bogdanzurac.marp.core.auth.CoreAuthModule
import dev.bogdanzurac.marp.core.data.CoreDataModule
import dev.bogdanzurac.marp.core.ui.CoreUiModule
import dev.bogdanzurac.marp.feature.crypto.data.FeatureCryptoDataModule
import dev.bogdanzurac.marp.feature.crypto.ui.FeatureCryptoUiModule
import dev.bogdanzurac.marp.feature.notes.data.FeatureNotesDataModule
import dev.bogdanzurac.marp.feature.notes.domain.FeatureNotesDomainModule
import dev.bogdanzurac.marp.feature.notes.ui.FeatureNotesUiModule
import dev.bogdanzurac.marp.feature.weather.data.FeatureWeatherDataModule
import dev.bogdanzurac.marp.feature.weather.domain.FeatureWeatherDomainModule
import dev.bogdanzurac.marp.feature.weather.ui.FeatureWeatherUiModule
import dev.bogdanzurac.marp.lib.db.firebase.LibDbFirebaseModule
import dev.bogdanzurac.marp.lib.flagging.firebase.LibFlaggingFirebaseModule
import dev.bogdanzurac.marp.lib.services.google.GoogleLocationProvider
import dev.bogdanzurac.marp.lib.services.google.libServicesGoogleModule
import dev.bogdanzurac.marp.lib.services.huawei.HuaweiLocationProvider
import dev.bogdanzurac.marp.lib.services.huawei.isHuaweiDevice
import dev.bogdanzurac.marp.lib.services.huawei.libServicesHuaweiModule
import dev.bogdanzurac.marp.lib.tracking.firebase.LibTrackingFirebaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module

internal val koinModules = listOf(
    ElGoogAppModule().module,
    CoreAuthModule().module,
    CoreDataModule().module,
    CoreUiModule().module,
    FeatureCryptoDataModule().module,
    FeatureCryptoUiModule().module,
    FeatureNotesDataModule().module,
    FeatureNotesDomainModule().module,
    FeatureNotesUiModule().module,
    FeatureWeatherDataModule().module,
    FeatureWeatherDomainModule().module,
    FeatureWeatherUiModule().module,
    LibDbFirebaseModule().module,
    LibFlaggingFirebaseModule().module,
    libServicesGoogleModule,
    libServicesHuaweiModule,
    LibTrackingFirebaseModule().module,
)

@Module
@ComponentScan
class ElGoogAppModule {

    @Single
    fun locationProvider(
        googleLocationProvider: GoogleLocationProvider,
        huaweiLocationProvider: HuaweiLocationProvider
    ) =
        if (isHuaweiDevice()) huaweiLocationProvider
        else googleLocationProvider
}
