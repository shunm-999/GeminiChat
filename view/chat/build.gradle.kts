plugins {
    alias(libs.plugins.geminiChat.android.feature)
    alias(libs.plugins.geminiChat.android.screenshot.test)
}

android {
    namespace = "com.shunm.view.chat"
}

dependencies {
    implementation(project(":domain:common"))
    implementation(project(":domain:chat"))

    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.navigationSuite)
    implementation(libs.androidx.compose.material3.windowSizeClass)
}
