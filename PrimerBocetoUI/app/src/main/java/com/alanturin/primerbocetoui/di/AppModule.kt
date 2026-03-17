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
import com.alanturin.primerbocetoui.data.local.dao.AssistanceDao
import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.local.dao.HorarioDao
import com.alanturin.primerbocetoui.data.local.dao.SubjectDao
import com.alanturin.primerbocetoui.data.local.dao.TeacherDao
import com.alanturin.primerbocetoui.data.remote.clasesprofesor.ClasesProfesorRemoteDataSource
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepository
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepositoryImpl
import com.alanturin.primerbocetoui.data.remote.clasesalumno.ClasesAlumnoApi
import com.alanturin.primerbocetoui.data.remote.clasesalumno.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import com.alanturin.primerbocetoui.data.repository.ClasesAlumnoRepositoryImpl
import com.alanturin.primerbocetoui.data.remote.CalendarApi
import com.alanturin.primerbocetoui.data.remote.ClassSessionsApi
import com.alanturin.primerbocetoui.data.remote.EnrollmentApi
import com.alanturin.primerbocetoui.data.remote.GroupApi
import com.alanturin.primerbocetoui.data.remote.GroupRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.StudentWeekScheduleApi
import com.alanturin.primerbocetoui.data.remote.WeekScheduleApi
import com.alanturin.primerbocetoui.data.remote.assistance.AssistanceApi
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsApi
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.clasesalumno.ClasesAlumnoRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskApi
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.task.TaskApi
import com.alanturin.primerbocetoui.data.remote.task.TaskRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.task.TaskRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.teacher.TeacherApi
import com.alanturin.primerbocetoui.data.remote.teacher.TeacherRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.teacher.TeacherRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.repository.CalendarRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.EnrollmentRepository
import com.alanturin.primerbocetoui.data.repository.EnrollmentRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.GroupRepository
import com.alanturin.primerbocetoui.data.repository.GroupRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.classsessions.ClassSessionsRepository
import com.alanturin.primerbocetoui.data.repository.classsessions.ClassSessionsRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.studentweekschedule.StudentWeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.studentweekschedule.StudentWeekScheduleRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import com.alanturin.primerbocetoui.data.repository.task.TaskRepositoryImpl
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepository
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindEnrollmentRepository(impl: EnrollmentRepositoryImpl): EnrollmentRepository


    @Binds
    @Singleton
    abstract fun bindWeekScheduleRepository(impl: WeekScheduleRepositoryImpl): WeekScheduleRepository

    @Binds
    @Singleton
    abstract fun bindStudentWeekScheduleRepository(impl: StudentWeekScheduleRepositoryImpl): StudentWeekScheduleRepository

    @Binds
    @Singleton
    abstract fun bindClassSessionRepository(impl: ClassSessionsRepositoryImpl): ClassSessionsRepository

    @Binds
    @Singleton
    abstract fun bindAssistanceRepository(impl: AssistanceRepositoryImpl): AssistanceRepository

    @Binds
    @Singleton
    abstract fun bindAssistanceForStudentsRemoteDataSource(
        impl: AssistanceForStudentsRemoteDataSourceImpl
    ): AssistanceForStudentsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTaskRemoteDataSource(impl: TaskRemoteDataSourceImpl): TaskRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    abstract fun bindStudentTaskRemoteDataSource(impl: StudentTaskRemoteDataSourceImpl): StudentTaskRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStudentTaskRepository(impl: StudentTaskRepositoryImpl): StudentTaskRepository

    @Binds
    @Singleton
    abstract fun bindTeacherRemoteDataSource(impl: TeacherRemoteDataSourceImpl): TeacherRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTeacherRepository(impl: TeacherRepositoryImpl): TeacherRepository

    @Binds
    @Singleton
    abstract fun bindClasesAlumnoRemoteDataSource(
        impl: ClasesAlumnoRemoteDataSourceImpl
    ): ClasesAlumnoRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindClasesAlumnoRepository(impl: ClasesAlumnoRepositoryImpl): ClasesAlumnoRepository

    companion object {

        @Provides
        @Singleton
        fun provideClasesAlumnoApi(retrofit: Retrofit): ClasesAlumnoApi {
            return retrofit.create(ClasesAlumnoApi::class.java)
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

        /**
         * aqu las de room
         */
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ziryab_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun provideGroupDao(database: AppDatabase): GroupDao {
            return database.groupDao()
        }

        @Provides
        @Singleton
        fun provideHorarioDao(database: AppDatabase): HorarioDao {
            return database.horarioDao()
        }

        @Provides
        @Singleton
        fun provideTeacherDao(database: AppDatabase): TeacherDao {
            return database.teacherDao()
        }

        @Provides
        @Singleton
        fun provideAssistanceDao(database: AppDatabase): AssistanceDao {
            return database.assistanceDao()
        }

        @Provides
        @Singleton
        fun provideSubjectDao(database: AppDatabase): SubjectDao {
            return database.subjectDao()
        }

        /**
         * aqui más de remote
         */
        @Provides
        @Singleton
        fun provideEnrollmentApi(retrofit: Retrofit): EnrollmentApi = retrofit.create(EnrollmentApi::class.java)

        @Provides
        @Singleton
        fun provideWeekScheduleApi(retrofit: Retrofit): WeekScheduleApi = retrofit.create(WeekScheduleApi::class.java)

        @Provides
        @Singleton
        fun provideStudentWeekScheduleApi(retrofit: Retrofit): StudentWeekScheduleApi = retrofit.create(StudentWeekScheduleApi::class.java)

        @Provides
        @Singleton
        fun provideClassSessionsApi(retrofit: Retrofit): ClassSessionsApi =
            retrofit.create(ClassSessionsApi::class.java)

        @Provides
        @Singleton
        fun provideAssistanceApi(retrofit: Retrofit): AssistanceApi =
            retrofit.create(AssistanceApi::class.java)

        @Provides
        @Singleton
        fun provideAssistanceForStudentsApi(retrofit: Retrofit): AssistanceForStudentsApi =
            retrofit.create(AssistanceForStudentsApi::class.java)

        @Provides
        @Singleton
        fun provideTaskApi(retrofit: Retrofit): TaskApi =
            retrofit.create(TaskApi::class.java)

        @Provides
        @Singleton
        fun provideStudentTaskApi(retrofit: Retrofit): StudentTaskApi =
            retrofit.create(StudentTaskApi::class.java)

        @Provides
        @Singleton
        fun provideTeacherApi(retrofit: Retrofit): TeacherApi =
            retrofit.create(TeacherApi::class.java)
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