package com.example.newyorkarticle

import android.content.Context
import com.example.flickr.sample.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Retrofit2Client(context: Context) {

    private val instance: Retrofit2Client? = null
    private val retrofit: Retrofit
    private val client: OkHttpClient

    val apiInterface: ApiInterface

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(AppConstants.NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.readTimeout(AppConstants.NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()

                    .method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }

        client = httpClient.build()

        retrofit = Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    companion object {

        fun getInstance(context: Context): Retrofit2Client {
            /*if (instance == null) {
            instance = new Retrofit2Client(context);
        }*/
            return Retrofit2Client(context)
        }
    }

}
