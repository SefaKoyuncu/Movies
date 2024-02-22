// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

task("clean") {
    delete(project.buildDir)
}