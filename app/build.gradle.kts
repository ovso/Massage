@file:Suppress("SpellCheckingInspection")

import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.service.TagService
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.android.gms.oss-licenses-plugin")
    id("org.ajoberstar.grgit") version "4.0.2"
}
//apply from: '../ktlint.gradle'

val grGit: Grgit = Grgit.open(mapOf("currentDir" to project.rootDir))
fun getVersionName(grGit: Grgit): String {
    return TagService(grGit.repository).list().last().name
}

fun getVersionCode(grGit: Grgit): Int {
    return TagService(grGit.repository).list().size
}


val keystorePropertiesFile = rootProject.file("../jks/massage_keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {

    compileSdkVersion(Config.compileSdk)
    defaultConfig {
        applicationId = Config.appId
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = getVersionCode(grGit)
        versionName = getVersionName(grGit)
        testInstrumentationRunner = "androidx.test.ext.junit.runners.AndroidJUnit4"
        println("versionName = ${getVersionName(grGit)}")
        println("versionCode = ${getVersionCode(grGit)}")
        setProperty("archivesBaseName", "massage-$versionName")
    }

    signingConfigs {
        getByName("debug") {

        }

        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {

        getByName("debug") {
            resValue("string", "ads_app_id", "ca-app-pub-3940256099942544~3347511713")
            resValue("string", "ads_banner_unit_id", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "ads_interstitial_unit_id", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ads_native_unit_id", "ca-app-pub-3940256099942544/2247696110")
            resValue("string", "youtube_developer_key", "AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s")
            resValue("string", "kakao_auth", "KakaoAK 7296bb6b63d625d940275dbc7a78ae41")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            // global proguard settings
            proguardFile(file("proguard-rules.pro"))
            // library proguard settings
            val files = rootProject.file("proguard")
                .listFiles()
                ?.filter { it.name.startsWith("proguard") }
                ?.toTypedArray()

            proguardFiles(*files ?: arrayOf())


            resValue("string", "ads_app_id", "ca-app-pub-8679189423397017~4275586930")
            resValue("string", "ads_banner_unit_id", "ca-app-pub-8679189423397017/3924518551")
            resValue("string", "ads_interstitial_unit_id", "ca-app-pub-8679189423397017/5031831418")
            resValue("string", "ads_native_unit_id", "ca-app-pub-8679189423397017/9450162222")
            resValue("string", "youtube_developer_key", "AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s")
            resValue("string", "kakao_auth", "KakaoAK 7296bb6b63d625d940275dbc7a78ae41")

        }

    }

    buildFeatures {
        viewBinding = true
    }

    androidExtensions {
        isExperimental = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

/*
    sourceSets {
        main.java.srcDirs += "src/main/kotlin"
    }
*/

    kotlin {
        sourceSets["main"].apply {
            kotlin.srcDir("src/main/kotlin")
        }
    }
    lintOptions {
        disable("MissingTranslation")
    }

    packagingOptions {
        exclude("META-INF/kotlinx-io.kotlin_module")
        exclude("META-INF/atomicfu.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-io.kotlin_module")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":nativetemplates"))
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestUtil("androidx.test:orchestrator:1.3.0")
    implementation("androidx.appcompat:appcompat:1.3.0-alpha02")
    implementation("androidx.core:core-ktx:1.5.0-alpha04")
    implementation("com.google.android.material:material:1.3.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")


    implementation("com.androidhuman.rxfirebase2:firebase-database:16.0.1.0")
    implementation("com.google.firebase:firebase-bom:26.0.0")
    implementation("com.google.firebase:firebase-database:19.5.1")
    implementation("com.google.firebase:firebase-core:18.0.0")
    implementation("com.google.firebase:firebase-ads:19.5.0")
    implementation("com.google.firebase:firebase-analytics:18.0.0")

    implementation("com.google.guava:guava:28.2-android")

    implementation("com.github.bumptech.glide:glide:4.11.0")

    implementation("com.jakewharton:butterknife:10.2.1")
    annotationProcessor("com.jakewharton:butterknife-compiler:10.2.1")

    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.2.0")

    implementation("com.jakewharton.timber:timber:4.7.1")

    //noinspection AnnotationProcessorOnCompilePath
    compileOnly("org.projectlombok:lombok:1.16.20")
    annotationProcessor("org.projectlombok:lombok:1.16.20")

    implementation("jp.wasabeef:recyclerview-animators:2.2.7")

    implementation("de.psdev.licensesdialog:licensesdialog:2.0.0")

    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")

    implementation("com.github.chrisbanes:PhotoView:2.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5")
    implementation("com.github.chrisbanes:PhotoView:2.0.0")
}