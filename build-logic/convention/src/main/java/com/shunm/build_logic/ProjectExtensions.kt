package com.shunm.build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.pluginId(alias: String): String =
    findPlugin("ktlint-gradle").get().get().pluginId

fun Project.configureKotlinCompiler(configure: KotlinAndroidProjectExtension.() -> Unit) {
    with(extensions.getByType<KotlinAndroidProjectExtension>()) {
        configure()
    }
}

fun Project.configureComposeCompiler(configure : ComposeCompilerGradlePluginExtension.() -> Unit) {
    with(extensions.getByType(ComposeCompilerGradlePluginExtension::class.java)) {
        configure()
    }
}