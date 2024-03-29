plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.sefa.domain"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.junit.ktx)
    implementation(libs.androidx.test.ext.junit)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.annotationProcessor)
    ksp(libs.room.ksp)

    //Inject
    implementation(libs.inject)

    // Test
    testImplementation(project(":data"))
    testImplementation(project(":common-test"))

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
}