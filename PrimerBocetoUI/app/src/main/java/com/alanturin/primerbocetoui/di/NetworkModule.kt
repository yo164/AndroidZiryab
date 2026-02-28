package com.alanturin.primerbocetoui.di

import com.alanturin.primerbocetoui.data.remote.ClasesProfesorApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
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