package com.alanturin.primerbocetoui.di

import com.alanturin.primerbocetoui.data.remote.ClasesProfesorApi
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(sessionViewModel: SessionViewModel): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token: String? = sessionViewModel.token.value

                android.util.Log.d("ZIRYAB", "Token: $token")
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideClasesProfesorApi(retrofit: Retrofit): ClasesProfesorApi {
        return retrofit.create(ClasesProfesorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): com.alanturin.primerbocetoui.data.remote.AuthApi {
        return retrofit.create(com.alanturin.primerbocetoui.data.remote.AuthApi::class.java)
    }


}