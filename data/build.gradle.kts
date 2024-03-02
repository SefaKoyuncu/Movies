import java.util.Properties

plugins {
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.sefa.data"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
    }

    buildFeatures {
        buildConfig = true
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
    disabledRules.addAll("no-wildcard-imports")
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

    implementation(project(":domain"))

    // Retrofit
    implementation(libs.bundles.retrofit)

    // HttpClient
    implementation(libs.bundles.okhttp)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.annotationProcessor)
    ksp(libs.room.ksp)

    // Coroutines
    implementation(libs.bundles.coroutine)

    // Paging3
    implementation(libs.paging)

    //Inject
    implementation(libs.inject)

    // Testing
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