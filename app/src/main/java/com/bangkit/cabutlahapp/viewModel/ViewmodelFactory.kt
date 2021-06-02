package com.bangkit.cabutlahapp.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.di.Injection
import com.bangkit.cabutlahapp.ui.Detail.DetailViewModel
import com.bangkit.cabutlahapp.ui.hotel.HotelViewModel
import com.bangkit.cabutlahapp.ui.restaurant.RestaurantViewModel
import com.bangkit.cabutlahapp.ui.vacation.VacationViewModel

class ViewmodelFactory private constructor(private val repo: CabutlahRepo): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewmodelFactory? = null

        fun getInstance(): ViewmodelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewmodelFactory(Injection.getRepo()).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                return MapsViewModel(repo) as T
            }
            modelClass.isAssignableFrom(RestaurantViewModel::class.java) -> {
                return RestaurantViewModel(repo) as T
            }
            modelClass.isAssignableFrom(HotelViewModel::class.java) -> {
                return HotelViewModel(repo) as T
            }
            modelClass.isAssignableFrom(VacationViewModel::class.java) -> {
                return VacationViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repo) as T
            }
            else -> throw Throwable("Unknown Viewmodel Class: ${modelClass.name}")
        }
    }
}