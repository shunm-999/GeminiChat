plugins {
    alias(libs.plugins.geminiChat.android.feature)
    alias(libs.plugins.geminiChat.android.library.compose)
}

android {
    namespace = "com.shunm.view.chat"
}

dependencies {
    implementation(project(":domain:chat"))
}
