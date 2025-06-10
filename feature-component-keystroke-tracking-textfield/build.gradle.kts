plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")
apply(from = "$rootDir/sharedGradleFiles/composeDependencies.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield"
}

dependencies {}