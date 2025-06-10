plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")
apply(from = "$rootDir/sharedGradleFiles/composeDependencies.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.feature.gamescreen"
}

dependencies {
    implementation(project(":domain-data-typespeed"))
    implementation(project(":feature-component-keystroke-tracking-textfield"))
    implementation(project(":feature-component-wordsperminute"))
}