plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.junit5.plugin)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")
apply(from = "$rootDir/sharedGradleFiles/composeDependencies.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.foundation.ui"
}
