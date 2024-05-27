import java.util.Properties

plugins {
    alias(libs.plugins.geminiChat.android.library)
    alias(libs.plugins.geminiChat.android.hilt)
    alias(libs.plugins.geminiChat.android.room)
    id("kotlinx-serialization")
}

android {
    namespace = "com.shunm.infra.chat"

    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        val geminiApiKey = properties.getProperty("GEMINI_API_KEY")
        buildConfigField("String", "GEMINI_API_KEY", "\"${geminiApiKey}\"")
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(project(":domain:common"))
    api(project(":domain:chat"))
    api(project(":infra:database"))
    implementation(libs.google.generative.ai)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}
