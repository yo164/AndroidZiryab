package com.alanturin.primerbocetoui.di

import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.data.remote.ClasesProfesorRemoteDataSource
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepository
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindRemoteClasesDataSource(
        clasesRemoteDataSource: ClasesProfesorRemoteDataSource
    ): ClasesProfesorDataSource

    @Binds
    @Singleton
    abstract fun bindClasesRepository(
        clasesRepositoryImpl: ClasesProfesorRepositoryImpl
    ): ClasesProfesorRepository

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

