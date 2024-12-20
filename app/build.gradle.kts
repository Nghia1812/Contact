plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")

    //add these for hilt
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")

    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.contactappcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.contactappcompose"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.symbol.processing.api) // Replace with your versions


    implementation (libs.material3)
    implementation (libs.androidx.foundation)
    implementation (platform(libs.androidx.compose.bom.v20240202))

    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // for room database
    val room_version = "2.6.1"
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
//annotationProcessor(libs.androidx.room.compiler)
   // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

//    implementation ("androidx.room:room-runtime:$room_version")
//    kapt ("androidx.room:room-compiler:$room_version")
//    implementation ("androidx.room:room-ktx:$room_version")

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //viewModelScope
    //implementation(libs.androidx.lifecycle.viewmodel.ktx)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

//hilt
kapt {
    correctErrorTypes = true
}