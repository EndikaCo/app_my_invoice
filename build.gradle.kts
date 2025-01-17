buildscript {
    dependencies {
        //firebase auth
        classpath("com.google.gms:google-services:4.4.1")
    }

}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false

    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    //dagger hilt
    id("com.google.dagger.hilt.android") version "2.48" apply false

    //ksp
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

