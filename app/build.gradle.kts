plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/sharedGradleFiles/androidShared.gradle")
apply(from = "$rootDir/sharedGradleFiles/hiltDependencies.gradle")

android {
    namespace = "com.theatfabric.wordsperminute"

    defaultConfig {
        applicationId = "com.theatfabric.wordsperminute"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}