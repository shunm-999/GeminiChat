package com.shunm.build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.pluginId(alias: String): String =
    findPlugin(alias).get().get().pluginId

fun Project.kotlin(block : KotlinCompilationTask<*>.() -> Unit) {
    tasks.withType(KotlinCompilationTask::class.java) {
        block(this@withType)
    }
}
