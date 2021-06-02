package com.bangkit.cabutlahapp.ui.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.databinding.ActivityRestaurantBinding
import com.bangkit.cabutlahapp.viewModel.ViewmodelFactory
import com.google.firebase.database.*

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var restaurantRv: RecyclerView
    private lateinit var viewmodel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewmodelFactory.getInstance()
        viewmodel = ViewModelProvider(this, factory)[RestaurantViewModel::class.java]

        restaurantRv = findViewById(R.id.resto_list)
        restaurantRv.layoutManager = LinearLayoutManager(this)
        restaurantRv.setHasFixedSize(
                true
        )
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        viewmodel.getResto(this).observe(this, {
            if (it != null) {
                restaurantRv.adapter = RestaurantAdapter(it)
            }
        })
    }
}
