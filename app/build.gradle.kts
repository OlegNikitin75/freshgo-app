plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
//    alias(libs.plugins.google.services)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "alex.dev.freshgoapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "alex.dev.freshgoapp"
        minSdk = 24
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.ui.unit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
    // Koin Annotations and ksp compiler
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)
    // Kotlinx serialization
    implementation(libs.kotlinx.serialization)
    // Coil
    implementation(libs.coil3)
    implementation(libs.coil3.compose)
    implementation(libs.coil3.compose.core)
    implementation(libs.coil3.network.ktor)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    //Splashscreen
    implementation("androidx.core:core-splashscreen:1.2.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:3.0.0")
    implementation ("com.squareup.okhttp3:okhttp3:5.0.0-alpha2")
    implementation ("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")


    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
}