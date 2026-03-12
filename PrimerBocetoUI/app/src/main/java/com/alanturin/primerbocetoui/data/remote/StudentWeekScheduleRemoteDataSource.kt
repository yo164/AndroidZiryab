package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.StudentScheduleItemRemote
import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import javax.inject.Inject

class StudentWeekScheduleRemoteDataSource @Inject constructor(
    private val api: StudentWeekScheduleApi
) {

    suspend fun getWeekScheduleByStudent(idStudent: Long): Result<List<StudentScheduleItemRemote>>{
        return try {
            val response = api.getWeekScheduleByStudent(idStudent)
            if (response.isSuccessful){
                val body = response.body()
                if (body == null){
                    Result.failure(RuntimeException("Body vacío"))
                }else{
                    Result.success(body.data)
                }
            }else {
                Result.failure(RuntimeException("${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}