package com.bangkit.cabutlahapp.ui.hotel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.model.Hotel

class HotelViewModel(private val repo: CabutlahRepo): ViewModel() {

    fun getHotel(context: Context): LiveData<ArrayList<Hotel>> =
            repo.getHotelList(context)
}