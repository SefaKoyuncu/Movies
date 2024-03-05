import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.sefa.movies"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.sefa.movies"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
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

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature-details"))
    implementation(project(":feature-fav"))
    implementation(project(":feature-main"))
    implementation(project(":common-ui"))

    // Retrofit
    implementation(libs.bundles.retrofit)

    // HttpClient
    implementation(libs.bundles.okhttp)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.annotationProcessor)
    ksp(libs.room.ksp)

    // Navigation
    implementation(libs.bundles.navigation)

    // Dagger-Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.ksp)

    // Coroutines
    implementation(libs.bundles.coroutine)

    // LeakCanary
    debugImplementation(libs.leakcanary)
}
