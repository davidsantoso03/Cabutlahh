package com.bangkit.cabutlahapp.ui.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.databinding.ActivityDetailBinding
import com.google.android.gms.dynamic.IFragmentWrapper

class DetailActivity : AppCompatActivity() {
    private lateinit var bind :ActivityDetailBinding

    companion object{
        const val EXTRA_HOTEL = "extra_hotel"
        const val EXTRA_RESTAURANT ="extra_restaurant"
        const val EXTRA_VACATION  ="extra_vacation"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if(extras!=null){
            val restoId = extras.getInt(EXTRA_RESTAURANT)
            if (restoId!=null){

            }
        }
    }
}