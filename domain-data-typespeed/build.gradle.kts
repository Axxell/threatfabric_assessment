plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.domaindata.keystrokes"
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
}