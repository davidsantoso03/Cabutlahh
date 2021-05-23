package com.bangkit.cabutlahapp.retrofit

import com.bangkit.cabutlahapp.R
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("place/queryautocomplete/json")
    fun getPlace(
        @Query("input") text: String,
        @Path("key") key : String

        )

}