package com.alanturin.primerbocetoui.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton
import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.data.local.AppDatabase
import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.remote.ClasesProfesorRemoteDataSource
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepository
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepositoryImpl
import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoApi
import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import com.alanturin.primerbocetoui.data.repository.ClasesAlumnoRepositoryImpl
import com.alanturin.primerbocetoui.data.remote.CalendarApi
import com.alanturin.primerbocetoui.data.remote.GroupApi
import com.alanturin.primerbocetoui.data.remote.GroupRemoteDataSource
import com.alanturin.primerbocetoui.data.repository.CalendarRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.GroupRepository
import com.alanturin.primerbocetoui.data.repository.GroupRepositoryImpl
import com.alanturin.primerbocetoui.domain.repository.CalendarRepository
import dagger.hilt.android.qualifiers.ApplicationContext

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

    @Binds
    @Singleton
    abstract fun bindCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        groupRepositoryImpl: GroupRepositoryImpl
    ): GroupRepository


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

        @Provides
        @Singleton
        fun provideFirebaseAuth(): com.google.firebase.auth.FirebaseAuth {
            return com.google.firebase.auth.FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideCalendarApi(retrofit: Retrofit): CalendarApi {
            return retrofit.create(CalendarApi::class.java)
        }

        @Provides
        @Singleton
        fun provideGroupApi(retrofit: Retrofit): GroupApi {
            return retrofit.create(GroupApi::class.java)
        }

        @Provides
        @Singleton
        fun provideGroupRemoteDataSource(api: GroupApi): GroupRemoteDataSource {
            return GroupRemoteDataSource(api)
        }

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ziryab_db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideGroupDao(database: AppDatabase): GroupDao {
            return database.groupDao()
        }
    }

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: com.alanturin.primerbocetoui.data.repository.AuthRepositoryImpl
    ): com.alanturin.primerbocetoui.domain.repository.AuthRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource