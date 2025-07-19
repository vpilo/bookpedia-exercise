import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
//        val desktopMain by getting
        
//        androidMain.dependencies {
     //       implementation(libs.koin.android)
//        }
        commonMain.dependencies {
            implementation(project(":model"))
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.material3)
//            implementation(compose.ui)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
//            implementation(libs.androidx.lifecycle.viewmodel)
//            implementation(libs.androidx.lifecycle.runtime.compose)
//
//            implementation(libs.androidx.lifecycle.viewmodel)
//            implementation(libs.androidx.lifecycle.runtime.compose)
//            implementation(libs.jetbrains.compose.navigation)
//            implementation(libs.kotlinx.serialization.json)
//            implementation(libs.androidx.room.runtime)
//            implementation(libs.sqlite.bundled)
//            implementation(libs.koin.compose)
//            implementation(libs.koin.compose.viewmodel)
//            api(libs.koin.core)
//
            implementation(libs.bundles.ktor)
//            implementation(libs.bundles.coil)
        }
//        desktopMain.dependencies {
//            implementation(compose.desktop.currentOs)
//            implementation(libs.kotlinx.coroutines.swing)
//            implementation(libs.ktor.client.okhttp)
//        }
//        nativeMain.dependencies {
//        }
    }
}

android {
    namespace = "com.plcoding.bookpedia.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
