plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.hfad.guessinggame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hfad.guessinggame"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildToolsVersion = "34.0.0"

    buildFeatures {
        // khong dung viewBinding vi dataBinding da tao ra cung binding class
        // chi tru activity la khong tao theo dataBinding
        //viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {
    val navVersion = "2.7.2"
    val lifecycleVersion = "2.6.2"
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    //implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Compose
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.ui:ui-graphics:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")

    implementation("androidx.compose.material3:material3:1.1.2")
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation ("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation ("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation ("androidx.compose.material3:material3-window-size-class")

    // or skip Material Design and build directly on top of foundational components
    implementation ("androidx.compose.foundation:foundation")

    // Optional - Integration with activities
    implementation ("androidx.activity:activity-compose:1.6.1")
    // Optional - Integration with ViewModels
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Optional - Integration with LiveData
    implementation ("androidx.compose.runtime:runtime-livedata")

    debugImplementation("androidx.compose.ui:ui-tooling:1.5.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.2")


}