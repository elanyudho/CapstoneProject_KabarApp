package com.dicoding.kabar.utils

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    //this is our multipart request
    //we have to parameters on is name and other one is description
    @Multipart
    @POST("/send-image")
    @JvmSuppressWildcards
    fun uploadImage(@Part image: MultipartBody.Part?): Call<com.dicoding.kabar.utils.Response>

    @GET("/send-image")
    fun getResponse(@Query("status") status: String): Call<com.dicoding.kabar.utils.Response>

}