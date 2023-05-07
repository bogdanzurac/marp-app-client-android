plugins {
    alias(libs.plugins.marp.core)
}

android {
    namespace = "dev.bogdanzurac.marp.core.prompts"
}

dependencies {
      implementation(libs.marp.core)
}