package ru.itis.clientserverapp.network.di

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.clientserverapp.network.BuildConfig
import ru.itis.clientserverapp.network.DogsApi
import ru.itis.clientserverapp.network.interceptor.AuthorizationInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import ru.itis.clientserverapp.network.BuildConfig as networkConfig

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideDogsApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): DogsApi {
        val gsonFactory = GsonConverterFactory.create(gson)

        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.THEDOGAPI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)

        return builder.build().create(DogsApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        val builder = if (networkConfig.DEBUG) {
            getUnsafeOkHttpClientBuilder()
        } else {
            OkHttpClient.Builder()
        }

        builder.addInterceptor(authInterceptor)

        return builder.build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    @SuppressLint("CustomX509TrustManager")
    private fun getUnsafeOkHttpClientBuilder(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

}