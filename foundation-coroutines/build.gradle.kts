plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.foundation.coroutines"
}