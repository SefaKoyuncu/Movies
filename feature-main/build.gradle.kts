plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.sefa.feature_main"
    compileSdk = libs.versions.compile.sdk.get().toInt()

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

ktlint {
    debug.set(true)
    ignoreFailures.set(false)
    disabledRules.addAll("no-wildcard-imports", "final-newline")
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true
    source.setFrom("src/main/java", "src/main/kotlin")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    this.jvmTarget = "1.8"
    jdkHome.set(file("path/to/jdkHome"))
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    this.jvmTarget = "1.8"
    jdkHome.set(file("path/to/jdkHome"))
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
    testImplementation(libs.coroutines.test)

    // Paging3
    implementation(libs.paging)

    // ViewModel-LiveData
    implementation(libs.bundles.viewmodel)
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    // Test
    testImplementation(project(":common-test"))

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
}