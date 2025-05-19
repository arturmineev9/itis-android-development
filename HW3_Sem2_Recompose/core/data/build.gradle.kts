plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "ru.itis.clientserverapp.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    dependencies {
        // Core Modules
        implementation(project(path = ":core:base"))
        implementation(project(path = ":core:domain"))
        implementation(project(path = ":core:network"))
        implementation(project(path = ":core:utils"))

        // AndroidX
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.core.ktx)

        // UI
        implementation(libs.material)

        // Network
        implementation(libs.bundles.network.deps)

        // Room
        implementation(libs.room)
        implementation(libs.room.ktx)
        ksp(libs.room.ksp)

        // Dependency Injection
        implementation(libs.hilt)
        ksp(libs.hilt.compiler)
    }
}
