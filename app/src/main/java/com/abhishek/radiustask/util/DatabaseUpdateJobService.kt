package com.abhishek.radiustask.util

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.abhishek.radiustask.api.ApiClient
import com.abhishek.radiustask.pojos.ExclusionsList
import com.abhishek.radiustask.pojos.ResponseData
import com.abhishek.radiustask.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatabaseUpdateJobService : JobService() {
    override fun onStartJob(jobParameters: JobParameters): Boolean {

        val dao = AppDatabase.getInstance(application)!!.dao()
        ApiClient.apiInterface.data().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>) {
                val responseData: ResponseData? = response.body()

                if (responseData != null) {
                    AppDatabase.databaseWriteExecutor.execute {
                        dao.deleteAllExclusionsData()
                        dao.insertAllExclusionsData(ExclusionsList.toObject(responseData.exclusions!!))

                    }
                    AppDatabase.databaseWriteExecutor.execute {
                        dao.deleteAllFacilitiesData()
                        dao.insertAllFacilitiesData(responseData.facilities!!)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {

            }
        })
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }
}