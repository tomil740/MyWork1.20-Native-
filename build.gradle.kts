// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.7.2"
        //   classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")


    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    //  id ("com.android.library' version") "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}