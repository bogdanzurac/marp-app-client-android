package dev.bogdanzurac.marp.app.elgoog.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

val ElGoogTypography =
    Typography().run {
        copy(
            displayLarge = this.displayLarge.copy(fontFamily = FontFamily.Default),
            displayMedium = this.displayMedium.copy(fontFamily = FontFamily.Default),
            displaySmall = this.displaySmall.copy(fontFamily = FontFamily.Default),
            headlineLarge = this.headlineLarge.copy(fontFamily = FontFamily.Default),
            headlineMedium = this.headlineMedium.copy(fontFamily = FontFamily.Default),
            headlineSmall = this.headlineSmall.copy(fontFamily = FontFamily.Default),
            titleLarge = this.titleLarge.copy(fontFamily = FontFamily.Default),
            titleMedium = this.titleMedium.copy(fontFamily = FontFamily.Default),
            titleSmall = this.titleSmall.copy(fontFamily = FontFamily.Default),
            bodyLarge = this.bodyLarge.copy(fontFamily = FontFamily.Default),
            bodyMedium = this.bodyMedium.copy(fontFamily = FontFamily.Default),
            bodySmall = this.bodySmall.copy(fontFamily = FontFamily.Default),
            labelLarge = this.labelLarge.copy(fontFamily = FontFamily.Default),
            labelMedium = this.labelMedium.copy(fontFamily = FontFamily.Default),
            labelSmall = this.labelSmall.copy(fontFamily = FontFamily.Default),
        )
    }