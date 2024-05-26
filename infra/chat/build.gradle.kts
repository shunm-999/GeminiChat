plugins {
    alias(libs.plugins.geminiChat.android.library)
    alias(libs.plugins.geminiChat.android.hilt)
    alias(libs.plugins.geminiChat.android.room)
    id("kotlinx-serialization")
}

android {
    namespace = "com.shunm.infra.chat"
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

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}
