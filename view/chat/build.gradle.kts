plugins {
    alias(libs.plugins.geminiChat.android.feature)
}

android {
    namespace = "com.shunm.view.chat"
}

dependencies {
    implementation(project(":domain:chat"))
}
