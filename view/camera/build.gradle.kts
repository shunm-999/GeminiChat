plugins {
    alias(libs.plugins.geminiChat.android.library)
    alias(libs.plugins.geminiChat.android.library.compose)
}

android {
    namespace = "com.shunm.view.camera"
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    implementation(project(":view:common-compose"))
    implementation(project(":domain:common"))
    implementation(project(":domain:chat"))

    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.navigationSuite)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.bundles.androidx.camerax)
}
