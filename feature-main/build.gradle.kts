plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.sefa.feature_main"
    compileSdk =libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.junit.ktx)
    implementation(libs.fragment)

    implementation(project(":domain"))
    implementation(project(":common-ui"))

    // Dagger-Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.ksp)

    // Coroutines
    implementation(libs.bundles.coroutine)

    // Paging3
    implementation(libs.paging)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // ViewModel-LiveData
    implementation(libs.bundles.viewmodel)
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    // Test
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.bundles.androidx.android.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.android.core.testing)
    androidTestImplementation(libs.espresso.core)
}