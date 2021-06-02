package com.bangkit.cabutlahapp.ui.restaurant
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.model.Restaurant
class RestaurantViewModel(private val repo: CabutlahRepo): ViewModel() {

    fun getResto(context: Context): LiveData<ArrayList<Restaurant>> =
            repo.getRestoList(context)
}