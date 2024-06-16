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
}
