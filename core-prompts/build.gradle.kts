plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.prompts"
}

dependencies {
      implementation(project(parts.core))
}