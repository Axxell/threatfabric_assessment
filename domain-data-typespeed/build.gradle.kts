plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.junit5.plugin)
}

apply(from = "$rootDir/sharedGradleFiles/libraryModule.gradle")

android {
    namespace = "com.theatfabric.wordsperminute.domaindata.keystrokes"
}

dependencies {
    implementation(project(":foundation-coroutines"))
    implementation(project(":foundation-strings"))

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
}