plugins {
    alias(libs.plugins.geminiChat.android.library)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.shunm.geminiChat.view.common"
}

dependencies {
    implementation(project(":domain:common"))

    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
}
