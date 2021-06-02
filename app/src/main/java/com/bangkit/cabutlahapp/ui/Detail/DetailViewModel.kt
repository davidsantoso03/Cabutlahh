package com.bangkit.cabutlahapp.ui.Detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.data.model.Vacation

class DetailViewModel(private val repo: CabutlahRepo): ViewModel() {

    fun getHotelDetail(context: Context, title: String): LiveData<Hotel> =
            repo.getHotelDetail(context, title)

    fun getRestoDetail(context: Context, title: String): LiveData<Restaurant> =
            repo.getRestoDetail(context, title)

    fun getVacationDetail(context: Context, title: String): LiveData<Vacation> =
            repo.getVacationDetail(context, title)
}