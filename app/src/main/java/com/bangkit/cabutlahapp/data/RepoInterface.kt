package com.bangkit.cabutlahapp.data

import android.content.Context
import androidx.lifecycle.LiveData
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
}