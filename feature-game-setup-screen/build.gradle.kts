plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.junit5.plugin)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")
apply(from = "$rootDir/sharedGradleFiles/composeDependencies.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.feature.initialscreen"
}

dependencies {
    implementation(project(":domain-data-typespeed"))
}
