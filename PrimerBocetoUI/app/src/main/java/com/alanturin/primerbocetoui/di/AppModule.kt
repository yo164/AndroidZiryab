package com.alanturin.primerbocetoui.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton
import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.data.remote.ClasesProfesorRemoteDataSource
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepository
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepositoryImpl
import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoApi
import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import com.alanturin.primerbocetoui.data.repository.ClasesAlumnoRepositoryImpl

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


    companion object {

        @Provides
        @Singleton
        fun provideClasesAlumnoApi(retrofit: Retrofit): ClasesAlumnoApi {
            return retrofit.create(ClasesAlumnoApi::class.java)
        }

        @Provides
        @Singleton
        fun provideClasesAlumnoRemoteDataSource(api: ClasesAlumnoApi): ClasesAlumnoRemoteDataSource {
            return ClasesAlumnoRemoteDataSource(api)
        }

        @Provides
        @Singleton
        fun provideClasesAlumnoRepository(
            dataSource: ClasesAlumnoRemoteDataSource
        ): ClasesAlumnoRepository {

            return ClasesAlumnoRepositoryImpl(dataSource)
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource