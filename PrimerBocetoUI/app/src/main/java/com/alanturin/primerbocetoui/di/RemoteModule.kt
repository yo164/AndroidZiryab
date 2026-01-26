package com.alanturin.primerbocetoui.di

import com.alanturin.primerbocetoui.data.remote.ClasesProfesorApi
import com.google.android.datatransport.runtime.dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Provides
@Singleton
fun provideClasesProfesorApi(retrofit: Retrofit): ClasesProfesorApi {
    return retrofit.create(ClasesProfesorApi::class.java)
}

