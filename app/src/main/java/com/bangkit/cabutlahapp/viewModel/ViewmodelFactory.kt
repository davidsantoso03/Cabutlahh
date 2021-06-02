package com.bangkit.cabutlahapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.di.Injection

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
            else -> throw Throwable("Unknown Viewmodel Class: ${modelClass.name}")
        }
    }
}