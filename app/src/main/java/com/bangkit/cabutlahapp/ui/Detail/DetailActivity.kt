package com.bangkit.cabutlahapp.ui.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.databinding.ActivityDetailBinding
import com.bangkit.cabutlahapp.ui.maps.MapsDetailActivity
import com.bangkit.cabutlahapp.viewModel.ViewmodelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.dynamic.IFragmentWrapper

class DetailActivity : AppCompatActivity() {
    private lateinit var bind :ActivityDetailBinding
    private lateinit var viewmodel: DetailViewModel

    private var latitude = 0.0
    private var longitude = 0.0
    private var name = ""

    companion object{
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_TYPE ="extra_type"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewmodelFactory.getInstance()
        viewmodel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val title = intent.getStringExtra(EXTRA_TITLE) as String
        val type = intent.getStringExtra(EXTRA_TYPE) as String

        when (type) {
            "Restaurant" -> {
                viewmodel.getRestoDetail(this, title).observe(this, {
                    if (it != null){
                        bind.apply {
                            detailName.text = it.nama
                            detailAddress.text = it.alamat
                            detailDescription.text = it.deskripsi

                            Glide.with(this@DetailActivity)
                                    .load(it.foto)
                                    .into(imageView)
                        }

                        setMap(it.latitude as Double, it.longitude as Double, it.nama as String
                        )
                    }
                })
            }
            "hotel" -> {
                viewmodel.getHotelDetail(this, title).observe(this, {
                    if (it != null){
                        bind.apply {
                            detailName.text = it.nama
                            detailDescription.text = it.deskripsi
                            detailAddress.text = it.alamat

                            Glide.with(this@DetailActivity)
                                    .load(it.foto)
                                    .into(imageView)
                        }

                        setMap(it.latitude as Double, it.longitude as Double, it.nama as String)
                    }
                })
            }
            "wisata" -> {
                viewmodel.getVacationDetail(this, title).observe(this, {
                    if (it != null){
                        bind.apply {
                            detailName.text = it.nama
                            detailDescription.text = it.deskripsi
                            detailAddress.text = it.alamat

                            Glide.with(this@DetailActivity)
                                    .load(it.foto)
                                    .into(imageView)
                        }

                        setMap(it.latitude as Double, it.longitude as Double, it.nama as String)
                    }
                })
            }
        }

        bind.btnShowLocation.setOnClickListener {
            val intent = Intent(this, MapsDetailActivity::class.java)
            intent.apply {
                putExtra(MapsDetailActivity.LATITUDE, latitude)
                putExtra(MapsDetailActivity.LONGITUDE, longitude)
                putExtra(MapsDetailActivity.TITLE, title)
            }

            startActivity(intent)
        }

    }

    private fun setMap(lat: Double, long: Double, title: String){
        latitude = lat
        longitude = long
        name = title
    }
}