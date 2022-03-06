plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = false
    )
}

dependencies {
    // Kotlin
    implementation(Libs.Kotlin.stdLib)
    implementation(Libs.Kotlin.coroutines)

    // Android X
    implementation(Libs.AndroidX.coreKtx)

    // Retrofit
    implementation(Libs.Retrofit.retrofit2)
    implementation(Libs.Retrofit.converterGson)

    // OkHttp
    implementation(Libs.Okhttp3.urlconnection)
    implementation(Libs.Okhttp3.loggingInterceptor)
}