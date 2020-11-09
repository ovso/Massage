// Top-level build file where you can add configuration options common to all sub-projects/modules.
//project.ext.compileSdkVersion = '27'

buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha15")
        classpath("com.jakewharton.hugo:hugo-plugin:1.2.1")
        classpath("com.google.gms:google-services:4.3.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.2")
    }

}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//apply from: 'versioning.gradle'
