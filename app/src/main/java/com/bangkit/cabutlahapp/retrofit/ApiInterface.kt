package com.bangkit.cabutlahapp.retrofit

import com.bangkit.cabutlahapp.R
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("maps/api/place/nearbysearch/{output}")
    fun getPlaces(
            @Path("output") output: String,
            @Query("location") location: String,
            @Query("type") type: String,
            @Query("radius") radius: Int,
            @Query("key") key: String
    ): Call<MapResponse>

}