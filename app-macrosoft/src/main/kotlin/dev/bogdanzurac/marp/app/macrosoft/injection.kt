package dev.bogdanzurac.marp.app.macrosoft

import dev.bogdanzurac.marp.core.auth.CoreAuthModule
import dev.bogdanzurac.marp.core.data.CoreDataModule
import dev.bogdanzurac.marp.core.services.LocationProvider
import dev.bogdanzurac.marp.core.ui.CoreUiModule
import dev.bogdanzurac.marp.feature.crypto.data.FeatureCryptoDataModule
import dev.bogdanzurac.marp.feature.crypto.ui.FeatureCryptoUiModule
import dev.bogdanzurac.marp.feature.news.data.FeatureNewsDataModule
import dev.bogdanzurac.marp.feature.news.data.newsapi.FeatureNewsDataWebNewsApiModule
import dev.bogdanzurac.marp.feature.news.ui.FeatureNewsUiModule
import dev.bogdanzurac.marp.feature.notes.domain.DummyNotesRepository
import dev.bogdanzurac.marp.feature.notes.domain.FeatureNotesDomainModule
import dev.bogdanzurac.marp.feature.notes.domain.NotesRepository
import dev.bogdanzurac.marp.feature.weather.data.FeatureWeatherDataModule
import dev.bogdanzurac.marp.feature.weather.domain.FeatureWeatherDomainModule
import dev.bogdanzurac.marp.feature.weather.ui.FeatureWeatherUiModule
import dev.bogdanzurac.marp.lib.db.firebase.LibDbFirebaseModule
import dev.bogdanzurac.marp.lib.flagging.firebase.LibFlaggingFirebaseModule
import dev.bogdanzurac.marp.lib.services.google.GoogleLocationProvider
import dev.bogdanzurac.marp.lib.services.google.libServicesGoogleModule
import dev.bogdanzurac.marp.lib.tracking.firebase.LibTrackingFirebaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module

internal val koinModules = listOf(
    MacrosoftAppModule().module,
    CoreAuthModule().module,
    CoreDataModule().module,
    CoreUiModule().module,
    FeatureCryptoDataModule().module,
    FeatureCryptoUiModule().module,
    FeatureNewsDataModule().module,
    FeatureNewsDataWebNewsApiModule().module,
    FeatureNewsUiModule().module,
    FeatureNotesDomainModule().module,
    FeatureWeatherDataModule().module,
    FeatureWeatherDomainModule().module,
    FeatureWeatherUiModule().module,
    LibDbFirebaseModule().module,
    LibFlaggingFirebaseModule().module,
    libServicesGoogleModule,
    LibTrackingFirebaseModule().module,
)

@Module
@ComponentScan
class MacrosoftAppModule {

    @Single
    fun locationProvider(googleLocationProvider: GoogleLocationProvider): LocationProvider =
        googleLocationProvider

    @Single
    fun notesRepository(): NotesRepository = DummyNotesRepository()
}
