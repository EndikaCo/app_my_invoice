
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //dagger hilt
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")

    //google services
    id("com.google.gms.google-services")

}

android {
    namespace = "com.endcodev.myinvoice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.endcodev.myinvoice"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-android:1.2.0")

    //Testing

    // Required -- JUnit 4 framework
    testImplementation("junit:junit:4.13.2")
    // Optional -- Robolectric environment
    //testImplementation ("androidx.test:core:1.5.0")
    // Mockito framework
    testImplementation ("org.mockito:mockito-core:5.10.0")
    // Optional -- mockito-kotlin
    //testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    // Optional -- Mockk framework
    testImplementation ("io.mockk:mockk:1.13.9")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //constraint layout
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.google.firebase:firebase-common-ktx:20.4.2")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //viewModel()
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    //observeAsState (liveData)
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")

    //dagger hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //room
    val roomVersion = "2.6.1"
    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation ("androidx.room:room-runtime:$roomVersion")
    annotationProcessor ("androidx.room:room-compiler:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")

    //coil
    implementation ("io.coil-kt:coil-compose:2.5.0")

    //Json
    implementation ("com.google.code.gson:gson:2.10.1")

    //extended icons
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
}