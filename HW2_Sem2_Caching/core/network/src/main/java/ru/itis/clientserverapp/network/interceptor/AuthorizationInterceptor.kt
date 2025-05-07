package ru.itis.clientserverapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.itis.clientserverapp.network.BuildConfig
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
