// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
    repositories{
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false

}

