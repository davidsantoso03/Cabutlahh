package com.bangkit.cabutlahapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.retrofit.MapResponse
import com.bangkit.cabutlahapp.retrofit.ResultsItem

interface RepoInterface {

    fun getNearbyPlaces(
        context: Context,
        type: String,
        location: String,
        output: String,
        key: String
    ): LiveData<MapResponse>

    fun getHotelList(context: Context): LiveData<ArrayList<Hotel>>

    fun getRestoList(context: Context): LiveData<ArrayList<Restaurant>>

    fun getVacationList(context: Context): LiveData<ArrayList<Vacation>>

    fun getHotelDetail(context: Context, title: String): LiveData<Hotel>

    fun getRestoDetail(context: Context, title: String): LiveData<Restaurant>

    fun getVacationDetail(context: Context, title: String): LiveData<Vacation>
}