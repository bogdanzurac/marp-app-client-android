import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.prompts"
}

dependencies {
      implementation(project(projects.core))
}