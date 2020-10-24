package com.pratthamarora.moviedb_clone.di.module

import com.pratthamarora.moviedb_clone.BuildConfig
import com.pratthamarora.moviedb_clone.data.remote.MovieService
import com.pratthamarora.moviedb_clone.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideTmdbApiInterceptor(@Named("tmdb_api_key") apiKey: String): Interceptor =
        Interceptor.invoke { chain ->
            val request = chain.request()
            val url = request.url
            val builder = url.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()
            val newRequest = request.newBuilder()
                .url(builder)
                .build()
            chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    @Named("tmdb_api_key")
    fun provideTmdbApiKey(): String = BuildConfig.TMDB_API_KEY

    @Singleton
    @Provides
    fun provideCallFactory(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tmdbApiInterceptor: Interceptor
    ): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(tmdbApiInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        interceptor: Call.Factory,
        moshi: MoshiConverterFactory,
        rxJava: RxJava3CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .callFactory(interceptor)
        .addConverterFactory(moshi)
        .addCallAdapterFactory(rxJava)
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}
