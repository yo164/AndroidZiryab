package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import javax.inject.Inject

class WeekScheduleRemoteDataSource @Inject constructor(
    private val api: WeekScheduleApi
) {
    suspend fun getWeekScheduleByAssignment(idTeacherAssignment: Long): Result<List<WeekScheduleItemRemote>> {
        return try {
            val response = api.getWeekScheduleByAssignment(idTeacherAssignment)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body.data)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWeekScheduleByTeacher(idTeacher: Long): Result<List<WeekScheduleItemRemote>>{
        return try {
            val response = api.getWeekScheduleByTeacher(idTeacher)
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