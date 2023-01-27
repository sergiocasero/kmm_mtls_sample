plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Shared.Main.coroutines)
                implementation(Dependencies.Shared.Main.serialization)
                implementation(Dependencies.Shared.Main.ktorClientCore)
                implementation(Dependencies.Shared.Main.ktorContentNegotiation)
                implementation(Dependencies.Shared.Main.ktorClientJson)
                implementation(Dependencies.Shared.Main.ktorClientAuth)
                implementation(Dependencies.Shared.Main.ktorLogging)

                with(Dependencies.DI) {
                    implementation(koinCore)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Shared.Android.ktorClientCore)
                implementation(Dependencies.Shared.Android.sslContext)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.Shared.Native.ktorClientCore)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.worldline.kmm_mtls_sample"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}