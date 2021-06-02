package com.bangkit.cabutlahapp.ui.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.databinding.ActivityMapsBinding
import com.bangkit.cabutlahapp.viewModel.MapsViewModel
import com.bangkit.cabutlahapp.viewModel.ViewmodelFactory
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var bind: ActivityMapsBinding
    private var mMarker: Marker? = null
    private lateinit var lastLocation: Location
    private lateinit var viewmodel : MapsViewModel

    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var pBar: ProgressBar? = null
    private var mLatitude = 0.0
    private var mLongitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spin = bind.spinner
        pBar = bind.progressBar

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getLocationAccess()){
                getLocationRequest()
                getLocationCallback()

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            }
        } else {
            getLocationRequest()
            getLocationCallback()

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }

        val factory = ViewmodelFactory.getInstance()
        viewmodel = ViewModelProvider(this, factory)[MapsViewModel::class.java]

        val myAdapter = ArrayAdapter(
                this@MapsActivity,
                android.R.layout.simple_list_item_1, resources.getStringArray(R.array.nearby_location)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = myAdapter
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> nearbyPlace(this@MapsActivity, "restaurant")
                    2 -> nearbyPlace(this@MapsActivity, "hospital")
                    3 -> nearbyPlace(this@MapsActivity, "art_gallery")
                    4 -> nearbyPlace(this@MapsActivity, "park")
                    5 -> nearbyPlace(this@MapsActivity, "hotel")
                    6 -> nearbyPlace(this@MapsActivity, "bus_station")
                    7 -> nearbyPlace(this@MapsActivity, "train_station")
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun nearbyPlace(context: Context, type: String) {

        mMap.clear()

        runProgressBar()
        viewmodel.getNearbyPlaces(context, type, "$mLatitude,$mLongitude", "json", key)
                .observe(this, {
                    if (it != null) {
                        if (it.status != "ZERO_RESULTS") {
                            for (i in it.results.indices) {
                                val markerOptions = MarkerOptions()
                                val item = it.results[i]

                                val lat = item.geometry.location.lat
                                val long = item.geometry.location.lng
                                val name = item.name

                                markerOptions.position(LatLng(lat, long))
                                markerOptions.title(name)

                                when (type) {
                                    "restaurant" -> markerOptions.icon(
                                            BitmapDescriptorFactory.fromAsset(
                                                    "restaurant.bmp"
                                            )
                                    )
                                    "art_gallery" -> markerOptions.icon(
                                            BitmapDescriptorFactory.fromAsset(
                                                    "art.bmp"
                                            )
                                    )
                                    "park" -> markerOptions.icon(
                                            BitmapDescriptorFactory.fromAsset(
                                                    "park.bmp"
                                            )
                                    )
                                    "hospital" -> markerOptions.icon(BitmapDescriptorFactory.fromAsset("hospital_2.bmp"))
                                    "hotel" -> markerOptions.icon(BitmapDescriptorFactory.fromAsset("hotel.bmp"))
                                    "bus_station" -> markerOptions.icon(BitmapDescriptorFactory.fromAsset("bus.bmp"))
                                    "train_station" -> markerOptions.icon(BitmapDescriptorFactory.fromAsset("train.bmp"))
                                }

                                mMap.addMarker(markerOptions)
                            }
                            stopProgressBar()
                        } else {
                            Toast.makeText(
                                    this,
                                    "Tidak ada $type dalam radius 1500 meter",
                                    Toast.LENGTH_LONG
                            ).show()

                            stopProgressBar()
                        }

                    } else {
                        Toast.makeText(this, "List Empty", Toast.LENGTH_LONG).show()
                        stopProgressBar()
                    }
                })


    }


    private fun getLocationAccess(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
            ) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST
                )
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST
                )
            }
            false
        } else
            true
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                                    this,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                    )
                        if (getLocationAccess()) {
                            mMap.isMyLocationEnabled = true
                        }
                } else {
                    Toast.makeText(
                            this,
                            "User Not granted location access permission",
                            Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
    }


    private fun getLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    lastLocation = locationResult.lastLocation

                    mLatitude = lastLocation.latitude
                    mLongitude = lastLocation.longitude

                    val markerOptions = MarkerOptions().position(LatLng(mLatitude, mLongitude))

                    mMarker = mMap.addMarker(markerOptions)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mLatitude, mLongitude)))
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
                }
            }

        }
    }

    private fun getLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.smallestDisplacement = 10f
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }
        } else {
            mMap.isMyLocationEnabled = true
        }

        mMap.uiSettings.isZoomControlsEnabled
    }

    override fun onLocationChanged(location: Location) {
        mLatitude = location.latitude
        mLongitude = location.longitude
        val latLng = LatLng(mLatitude, mLongitude)
        mMap.moveCamera(
                CameraUpdateFactory.newLatLng(latLng)
        )
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
        stopProgressBar()
    }

    override fun onStop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }


    private fun stopProgressBar() {
        pBar!!.visibility = View.GONE
    }

    private fun runProgressBar() {
        pBar!!.visibility = View.VISIBLE
    }

    companion object{
        const val key = "AIzaSyCSnO1yGSqsxR1gaO-Cl_EaBjnJ0YQP9Go"
    }

}




