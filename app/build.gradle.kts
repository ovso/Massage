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

    signingConfigs {
        release {
            keyAlias keystoreProperties ['keyAlias']
            keyPassword keystoreProperties ['keyPassword']
            storeFile file (keystoreProperties['storeFile'])
            storePassword keystoreProperties ['storePassword']
        }
    }
    compileSdkVersion rootProject . compile_sdk_version
            //buildToolsVersion rootProject.build_tools_version
            defaultConfig {
                applicationId rootProject . application_id
                        minSdkVersion rootProject . min_sdk_version
                        targetSdkVersion rootProject . target_sdk_version
                        versionCode rootProject . version_code

                        int versionMajor = 0
                int versionMinor = 0
                int versionPatch = rootProject . version_code

                        if (1000 > versionPatch && versionPatch > 99) {
                            versionMajor = versionPatch / 100
                            versionMinor = (versionPatch % 100) / 10
                            versionPatch = (versionPatch % 100) % 10
                        } else if (100 > versionCode && versionPatch > 9) {
                            versionMajor = 0
                            versionMinor = versionPatch / 10
                            versionPatch = versionPatch % 10
                        } else if (versionPatch < 10) {
                            versionMajor = 0
                            versionMinor = 0
                            versionPatch = versionPatch / 1
                        }

                versionName "${versionMajor}.${versionMinor}.${versionPatch}"
                testInstrumentationRunner 'androidx.test.ext.junit.runners.AndroidJUnit4'

                testOptions {
                    execution 'ANDROIDX_TEST_ORCHESTRATOR'
                }
            }

    buildTypes {
        debug {
            minifyEnabled false
            shrinkResources false
            proguardFile getDefaultProguardFile ('proguard-android.txt')
            proguardFile 'proguard-rules.pro'
            proguardFile 'proguard-tedpermission.pro'
            proguardFile 'proguard-firebase.pro'
            proguardFile 'proguard-butterknipe.pro'
            proguardFile 'proguard-guava.pro'
            proguardFile 'proguard-glide.pro'
            proguardFile 'proguard-dagger.pro'

            resValue("string", "ads_app_id", "ca-app-pub-3940256099942544~3347511713")
            resValue("string", "ads_banner_unit_id", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "ads_interstitial_unit_id", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ads_native_unit_id", "ca-app-pub-3940256099942544/2247696110")
            resValue("string", "youtube_developer_key", "AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s")
            resValue("string", "kakao_auth", "KakaoAK 7296bb6b63d625d940275dbc7a78ae41")
        }

        release {
            signingConfig signingConfigs . release
                    minifyEnabled false
            shrinkResources false
            proguardFile getDefaultProguardFile ('proguard-android.txt')
            proguardFile 'proguard-rules.pro'
            proguardFile 'proguard-tedpermission.pro'
            proguardFile 'proguard-firebase.pro'
            proguardFile 'proguard-rxfirebase.pro'
            proguardFile 'proguard-butterknipe.pro'
            proguardFile 'proguard-guava.pro'
            proguardFile 'proguard-glide.pro'
            proguardFile 'proguard-cauly.pro'
            proguardFile 'proguard-retrofit.pro'
            proguardFile 'proguard-dagger.pro'

            resValue("string", "ads_app_id", "ca-app-pub-8679189423397017~4275586930")
            resValue("string", "ads_banner_unit_id", "ca-app-pub-8679189423397017/3924518551")
            resValue("string", "ads_interstitial_unit_id", "ca-app-pub-8679189423397017/5031831418")
            resValue("string", "ads_native_unit_id", "ca-app-pub-8679189423397017/9450162222")
            resValue("string", "youtube_developer_key", "AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s")
            resValue("string", "kakao_auth", "KakaoAK 7296bb6b63d625d940275dbc7a78ae41")
        }

    }

    repositories {
        maven {
            url 'https://maven.google.com'
            // Alternative URL is 'https://dl.google.com/dl/android/maven2/'
        }
        maven { url 'https://maven.fabric.io/public' }
    }

    compileOptions {
        targetCompatibility JavaVersion . VERSION_1_8
                sourceCompatibility JavaVersion . VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        main.java.srcDirs += 'src/main/kotlin'
    }

    viewBinding {
        enabled = true
    }
}

dependencies {
    implementation fileTree (dir: 'libs', include: ['*.jar'])
    implementation project (":nativetemplates")
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13.1'
    androidTestImplementation 'androidx.test:runner:1.3.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestUtil 'androidx.test:orchestrator:1.3.0'

    implementation files ('libs/YouTubeAndroidPlayerApi.jar')
    implementation "androidx.appcompat:appcompat:1.3.0-alpha02"
    implementation "androidx.core:core-ktx:1.5.0-alpha04"
    implementation "com.google.android.material:material:1.3.0-alpha03"
    implementation "androidx.constraintlayout:constraintlayout:2.0.4"
    implementation "androidx.cardview:cardview:1.0.0"
    implementation 'com.google.android.gms:play-services-oss-licenses:17.0.0'


    implementation "com.androidhuman.rxfirebase2:firebase-database:16.0.1.0"
    implementation "com.google.firebase:firebase-bom:26.0.0"
    implementation "com.google.firebase:firebase-database:19.5.1"
    implementation "com.google.firebase:firebase-core:18.0.0"
    implementation "com.google.firebase:firebase-ads:19.5.0"
    implementation "com.google.firebase:firebase-analytics:18.0.0"

    implementation "com.google.guava:guava:28.2-android"

    implementation "com.github.bumptech.glide:glide:4.11.0"

    implementation "com.jakewharton:butterknife:10.2.1"
    annotationProcessor "com.jakewharton:butterknife-compiler:10.2.1"

    implementation "io.reactivex.rxjava2:rxjava:2.2.19"
    implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
    implementation "com.jakewharton.rxbinding2:rxbinding:2.2.0"

    implementation "com.jakewharton.timber:timber:4.7.1"

    //noinspection AnnotationProcessorOnCompilePath
    compileOnly "org.projectlombok:lombok:1.16.20"
    annotationProcessor "org.projectlombok:lombok:1.16.20"

    implementation "jp.wasabeef:recyclerview-animators:2.2.7"

    implementation "de.psdev.licensesdialog:licensesdialog:2.0.0"

    annotationProcessor "com.github.bumptech.glide:compiler:4.11.0"

    implementation "com.github.chrisbanes:PhotoView:2.0.0"

    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    implementation "com.squareup.okhttp3:logging-interceptor:4.9.0"
    implementation "com.squareup.okhttp3:okhttp:4.9.0"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation 'com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5'
    implementation 'com.github.chrisbanes:PhotoView:2.0.0'
}

// ADD THIS AT THE BOTTOM
apply plugin : 'com.google.gms.google-services'
