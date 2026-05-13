plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.alanturin.primerbocetoui"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alanturin.primerbocetoui"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

}

dependencies {
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    //workmanager
    //val work_version = "2.11.1"
    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    //camera
    implementation("androidx.camera:camera-compose:1.4.0-beta02")
    implementation("androidx.camera:camera-core:1.4.0-beta02")
    implementation("androidx.camera:camera-camera2:1.4.0-beta02")
    implementation("androidx.camera:camera-lifecycle:1.4.0-beta02")
    implementation("androidx.camera:camera-view:1.4.0-beta02")
    //View MOdel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)

    // Navegacion
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Nota: Eliminé las líneas hardcoded "com.squareup..." porque ya usas libs.retrofit

    // Coil
    implementation(libs.coil.compose)
    // implementation(libs.coil.network.okhttp)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.8.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.6")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.transport.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // MATERIAL ICONS EXTENDED
    implementation(libs.androidx.compose.material.icons.extended)

    //SERIALIZATION
    implementation(libs.kotlinx.serialization.json.v163)
    
    // FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Calendar
    implementation(libs.calendar.compose)
}