package com.bangkit.cabutlahapp.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.databinding.ActivityMapsDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapsDetailBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_detail) as SupportMapFragment
        mapFragment.getMapAsync(this)

        title = resources.getString(R.string.detail_location_title)

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        val lat = intent.getDoubleExtra(LATITUDE, 0.0)
        val long = intent.getDoubleExtra(LONGITUDE, 0.0)
        val title = intent.getStringExtra(TITLE) as String

        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(lat, long))
        markerOptions.title(title)

        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, long)))
        map.animateCamera(CameraUpdateFactory.zoomTo(16f))
    }

    companion object{
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val TITLE = "title"
    }
}