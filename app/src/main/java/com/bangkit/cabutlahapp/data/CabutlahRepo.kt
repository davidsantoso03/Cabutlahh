package com.bangkit.cabutlahapp.data

import android.content.Context
import androidx.lifecycle.LiveData
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


}