package com.bangkit.cabutlahapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.data.remote.RemoteSource
import com.bangkit.cabutlahapp.retrofit.MapResponse

class CabutlahRepo private constructor(private val remoteData: RemoteSource) : RepoInterface{

    companion object {
        @Volatile
        private var instance: CabutlahRepo? = null

        fun getInstance(remoteSource: RemoteSource): CabutlahRepo =
            instance ?: synchronized(this) {
                instance ?: CabutlahRepo(remoteSource).apply { instance = this }
            }
    }

    override fun getNearbyPlaces(
        context: Context,
        type: String,
        location: String,
        output: String,
        key: String
    ): LiveData<MapResponse> =
        remoteData.getNearbyPlaces(context, type, location, output, key)

    override fun getHotelList(context: Context): LiveData<ArrayList<Hotel>> =
            remoteData.getHotelList(context)

    override fun getRestoList(context: Context): LiveData<ArrayList<Restaurant>> =
            remoteData.getRestoList(context)

    override fun getVacationList(context: Context): LiveData<ArrayList<Vacation>> =
            remoteData.getVacationList(context)

    override fun getHotelDetail(context: Context, title: String): LiveData<Hotel> =
            remoteData.getHotelDetail(context, title)

    override fun getRestoDetail(context: Context, title: String): LiveData<Restaurant> =
            remoteData.getRestoDetail(context, title)

    override fun getVacationDetail(context: Context, title: String): LiveData<Vacation> =
            remoteData.getVacationDetail(context, title)

}