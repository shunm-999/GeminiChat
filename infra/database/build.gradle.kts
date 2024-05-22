plugins {
    alias(libs.plugins.geminiChat.android.library)
    alias(libs.plugins.geminiChat.android.hilt)
    alias(libs.plugins.geminiChat.android.room)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.shunm.infra.database"
}

dependencies {
    implementation(project(":domain:common"))
    implementation(libs.kotlinx.datetime)
}
