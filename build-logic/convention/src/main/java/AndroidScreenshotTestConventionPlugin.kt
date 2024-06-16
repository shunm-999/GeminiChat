import com.shunm.build_logic.libs
import com.shunm.build_logic.pluginId
import org.gradle.api.Plugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidScreenshotTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("compose-screenshot-test").get().get().pluginId)

                val extension = extensions.getByType<LibraryExtension>()
                with(extension) {
                    experimentalProperties["android.experimental.enableScreenshotTest"] = true
                }
            }
        }
    }
}