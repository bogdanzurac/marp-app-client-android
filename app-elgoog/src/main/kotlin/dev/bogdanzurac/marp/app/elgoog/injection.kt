package dev.bogdanzurac.marp.app.elgoog

import dev.bogdanzurac.marp.app.elgoog.core.location.locationDependencyModule
import dev.bogdanzurac.marp.core.auth.CoreAuthModule
import dev.bogdanzurac.marp.core.data.CoreDataModule
import dev.bogdanzurac.marp.core.ui.CoreUiModule
import dev.bogdanzurac.marp.tracking.firebase.LibTrackingFirebaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

internal val koinModules = listOf(
    ElGoogAppModule().module,
    CoreAuthModule().module,
    CoreDataModule().module,
    CoreUiModule().module,
    LibTrackingFirebaseModule().module,
    locationDependencyModule,
)

@Module
@ComponentScan
class ElGoogAppModule