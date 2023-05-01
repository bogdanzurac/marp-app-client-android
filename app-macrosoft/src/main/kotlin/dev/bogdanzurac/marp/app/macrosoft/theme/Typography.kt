package dev.bogdanzurac.marp.app.macrosoft.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

val MacrosoftTypography =
    Typography().run {
        copy(
            displayLarge = this.displayLarge.copy(fontFamily = FontFamily.Serif),
            displayMedium = this.displayMedium.copy(fontFamily = FontFamily.Serif),
            displaySmall = this.displaySmall.copy(fontFamily = FontFamily.Serif),
            headlineLarge = this.headlineLarge.copy(fontFamily = FontFamily.Serif),
            headlineMedium = this.headlineMedium.copy(fontFamily = FontFamily.Serif),
            headlineSmall = this.headlineSmall.copy(fontFamily = FontFamily.Serif),
            titleLarge = this.titleLarge.copy(fontFamily = FontFamily.Serif),
            titleMedium = this.titleMedium.copy(fontFamily = FontFamily.Serif),
            titleSmall = this.titleSmall.copy(fontFamily = FontFamily.Serif),
            bodyLarge = this.bodyLarge.copy(fontFamily = FontFamily.Serif),
            bodyMedium = this.bodyMedium.copy(fontFamily = FontFamily.Serif),
            bodySmall = this.bodySmall.copy(fontFamily = FontFamily.Serif),
            labelLarge = this.labelLarge.copy(fontFamily = FontFamily.Serif),
            labelMedium = this.labelMedium.copy(fontFamily = FontFamily.Serif),
            labelSmall = this.labelSmall.copy(fontFamily = FontFamily.Serif),
        )
    }