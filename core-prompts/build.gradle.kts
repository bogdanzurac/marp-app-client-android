import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.prompts"
}

dependencies {
      implementation(project(projects.core))
}