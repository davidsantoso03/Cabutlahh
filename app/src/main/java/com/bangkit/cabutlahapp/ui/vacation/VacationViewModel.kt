package com.bangkit.cabutlahapp.ui.vacation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cabutlahapp.data.CabutlahRepo
import com.bangkit.cabutlahapp.data.model.Vacation

class VacationViewModel(private val repo: CabutlahRepo): ViewModel() {

    fun getVacation(context: Context): LiveData<ArrayList<Vacation>> =
            repo.getVacationList(context)
}