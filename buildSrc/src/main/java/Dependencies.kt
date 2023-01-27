import org.gradle.api.Project

const val coroutines_version = "1.6.4"
const val kotlin_version = "1.7.20"
const val android_plugin_version = "7.3.1"
const val ktor_version = "2.1.3"
const val sqldelight_version = ""
const val espressoVersion = "3.5.1"
const val junitVersion = "4.12"
const val serialization_version = "1.4.1"
const val koinVersion = "3.2.2"
const val composeVersion = "1.3.2"
const val lifecycle = "2.5.0"
const val mapboxVersion = "10.10.0"

object App {
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val versionCode = 7
    const val versionName = "1.0.5"
    const val testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
}

object Common {
    const val minSdkVersion = App.minSdkVersion
    const val targetSdkVersion = App.targetSdkVersion
    const val testInstrumentationRunner = App.testInstrumentationRunner
}

object Dependencies {

    object DI {
        const val koinCore = "io.insert-koin:koin-core:${koinVersion}"
        const val koinAndroid = "io.insert-koin:koin-android:${koinVersion}"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:${koinVersion}"
    }

    object Shared {
        object Main {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version"

            const val ktorClientCore = "io.ktor:ktor-client-core:$ktor_version"
            const val ktorContentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktor_version"
            const val ktorClientJson = "io.ktor:ktor-client-json:$ktor_version"
            const val ktorSerialization = "io.ktor:ktor-client-serialization:$ktor_version"
            const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktor_version"
            const val ktorClientAuth = "io.ktor:ktor-client-auth:$ktor_version"
            const val ktorLogging = "io.ktor:ktor-client-logging:$ktor_version"
        }

        object Android {
            const val ktorClientCore = "io.ktor:ktor-client-okhttp:$ktor_version"
            const val sslContext = "io.github.hakky54:sslcontext-kickstart:7.4.9"

        }

        object Native {
            const val ktorClientCore = "io.ktor:ktor-client-ios:$ktor_version"
        }
    }
}

fun Project.getLocalProperty(name: String): String {
    val localProperties = java.util.Properties()
    localProperties
        .load(rootProject.file("local.properties").inputStream())

    return localProperties.getOrElse(name) {
        throw IllegalArgumentException("Define $name in your local.properties file")
    } as String
}
