package com.example.moregetandpostrequests

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("/custom-people/")
    fun getUser():Call<Array<DataItem>>
    @POST("/custom-people/")
    fun addUser(@Body userData:DataItem ):Call<DataItem>

}
