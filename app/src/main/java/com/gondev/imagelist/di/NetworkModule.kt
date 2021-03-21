package com.gondev.movie.di

import android.app.Application
import com.gondev.imagelist.BuildConfig
import com.gondev.imagelist.domain.network.ImageAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

/**
 * 네트워크 관련 모듈 등록 변수 입니다.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Singleton
	@Provides
	fun provideImageAPIService(application: Application) =
		Retrofit.Builder()
			.baseUrl(BuildConfig.base_url)
			.addConverterFactory( Json{
			if(!BuildConfig.DEBUG)
				ignoreUnknownKeys = true
		}.asConverterFactory("application/json".toMediaType()))
			.client(okhttpClient(application))
			.build()
			.create(ImageAPI::class.java)

	private fun okhttpClient(application: Application) =
		OkHttpClient.Builder().apply {
			cache(Cache(application.cacheDir, 10 * 1024 * 1024))
			connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
			writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
			readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
			retryOnConnectionFailure(true)
			addInterceptor(HttpLoggingInterceptor().apply {
				if (BuildConfig.DEBUG) {
					level = HttpLoggingInterceptor.Level.BODY
				}
			})
		}.build()
}
