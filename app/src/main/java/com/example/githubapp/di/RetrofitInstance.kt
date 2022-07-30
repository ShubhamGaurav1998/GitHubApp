package com.example.githubapp.di

import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitInstance {
    companion object {
        val BASE_URL: String = Constants.BASE_URL
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        @Provides
        @Singleton
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        @Provides
        @Singleton
        fun providesPrService(retrofit: Retrofit): GitHubService {
            return retrofit.create(GitHubService::class.java)
        }

    }
}
