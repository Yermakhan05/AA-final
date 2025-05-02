plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.ksp)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.myfaith"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myfaith"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures{
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
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.play.services.maps)
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
//    ksp(libs.room.compiler)
}