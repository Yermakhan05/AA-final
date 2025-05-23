plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.myfaith"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myfaith"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // подходящая версия для Compose 1.6.x
    }
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib")) {
                useVersion("1.9.24")
            }
        }
    }

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

//    implementation("com.github.Yermakhan05.alarmlibrary:1.1.3")
    implementation(libs.chatlib)


    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.junit.ktx)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.core.ktx)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Совместимо
    implementation("com.github.bumptech.glide:glide:4.16.0") // Актуально
    implementation("com.google.android.gms:play-services-location:21.0.1") // Актуально
    implementation("com.squareup.retrofit2:retrofit:2.11.0") // Актуально
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") // Совместимо
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Совместимо
    implementation("androidx.recyclerview:recyclerview:1.3.1") // Обновлено
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Обновлено
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // Обновлено до той же версии, что и logging-interceptor
    implementation("com.google.code.gson:gson:2.10.1") // Обновлено
    implementation("com.google.android.libraries.places:places:3.3.0")
    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.work.runtime)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:30.0.0"))
    implementation("com.google.firebase:firebase-analytics:21.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.1.0")

    // Firestore
    implementation ("com.google.firebase:firebase-firestore-ktx:24.4.5")

    // Gson
    implementation ("com.google.code.gson:gson:2.10.1")

    // Google Maps & Location
    implementation(libs.play.services.maps)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // JSON
    implementation("com.google.code.gson:gson:2.8.8")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // ViewModel & Coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Media3 (if needed, no .ktx version exists)
    implementation("androidx.media3:media3-common:1.3.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    implementation(libs.places)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.tooling)
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.coil.compose)


    debugImplementation ("androidx.fragment:fragment-testing:1.6.2")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.7.7")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
//    ksp(libs.room.compiler)
}
