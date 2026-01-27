plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.alanturin.primerbocetoui"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.alanturin.primerbocetoui"
        minSdk = 34
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- Android Core & Lifecycle ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- Jetpack Compose UI ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)

    // --- Navigation & Serialization ---
    implementation(libs.androidx.navigation.compose)

    // --- Hilt (Inyección de Dependencias) ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // IMPORTANTE: Usa ksp para el compilador
    implementation(libs.androidx.hilt.navigation.compose)

    // --- Networking (Retrofit) ---
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Nota: Eliminé las líneas hardcoded "com.squareup..." porque ya usas libs.retrofit

    // --- Imágenes (Coil) ---
    implementation(libs.coil.compose)
    // Si definiste coil-network-okhttp en el toml, déjalo, si no, bórralo:
    // implementation(libs.coil.network.okhttp)

    // --- Base de Datos (Room) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // --- Otros ---
    implementation(libs.transport.runtime)

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // --- Debugging ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // MATERIAL ICONS EXTENDED
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
}