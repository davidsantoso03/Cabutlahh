package com.bangkit.cabutlahapp.ui.vacation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.ui.hotel.HotelAdapter
import com.bangkit.cabutlahapp.ui.restaurant.VacationAdapter
import com.bangkit.cabutlahapp.viewModel.ViewmodelFactory
import com.google.firebase.database.*

class VacationActivity : AppCompatActivity() {
    private lateinit var Rv: RecyclerView
    private lateinit var viewmodel: VacationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewmodelFactory.getInstance()
        viewmodel = ViewModelProvider(this, factory)[VacationViewModel::class.java]
        Rv = findViewById(R.id.vacay_list)
        Rv.layoutManager = LinearLayoutManager(this)
        Rv.setHasFixedSize(
                true
        )
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        viewmodel.getVacation(this).observe(this, {
            if (it != null) {
                Rv.adapter = VacationAdapter(it)
            }
        })
    }
}