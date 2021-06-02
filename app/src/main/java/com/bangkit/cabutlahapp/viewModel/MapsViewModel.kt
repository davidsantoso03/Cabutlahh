package com.bangkit.cabutlahapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.retrofit.MapResponse
import com.bangkit.cabutlahapp.retrofit.ResultsItem

class MapsViewModel(private val repo: CabutlahRepo) : ViewModel() {

    fun getNearbyPlaces(context: Context,
                        type: String,
                        location: String,
                        output: String,
                        key: String): LiveData<MapResponse> =
        repo.getNearbyPlaces(context, type, location, output, key)
}