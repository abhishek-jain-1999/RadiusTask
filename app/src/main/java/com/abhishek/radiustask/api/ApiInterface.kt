package com.abhishek.radiustask.api

import com.abhishek.radiustask.pojos.ResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("iranjith4/ad-assignment/db")
    fun data(): Call<ResponseData>
}