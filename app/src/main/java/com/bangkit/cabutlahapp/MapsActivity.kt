package com.bangkit.cabutlahapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.cabutlahapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var bind: ActivityMapsBinding



    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    var pBar: ProgressBar? = null
    var mLatitude = 0.0
    var mLongitude = 0.0


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

        val myAdapter = ArrayAdapter(
                this@MapsActivity,
                android.R.layout.simple_list_item_1, resources.getStringArray(R.array.nearby_location)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = myAdapter
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var temp = ""
                if (position == 1) temp = "restaurant" else
                    if (position == 2) temp = "gym" else
                        if (position == 3) temp = "art_gallery" else
                            if (position == 4) temp = "museum" else
                                if (position == 5) temp = "amusement_park" else
                                    if (position == 6) temp = "city_hall" else
                                        if (position == 7) temp = "train_station" else
                                            if (position == 8) temp = "bus_station"
                                            else if (position != 0) {
                                                val sb =
                                                        "htpps://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                                                "location" + mLatitude + "," + mLongitude +
                                                                "&radius=5000" +
                                                                "&types" + temp +
                                                                "&key" + resources.getString(R.string.google_maps_key)
                                                bind.progressBar.visibility = View.VISIBLE
                                                PlacesTask().execute(sb)
                                            }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            getLocationUpdate()
            startLocationUpdate()
        } else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(this, "User Not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }


    private fun getLocationUpdate() {
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions = MarkerOptions().position(latLng)
                        mMap.addMarker(markerOptions)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                }
            }

        }
    }

    private fun startLocationUpdate() {
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
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled()
        getLocationAccess()
    }

    override fun onLocationChanged(location: Location) {
        mLatitude = location.latitude
        mLongitude = location.longitude
        val latLng = LatLng(mLatitude, mLongitude)
        mMap!!.moveCamera(
                CameraUpdateFactory.newLatLng(latLng)
        )
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f))
        stopProgressBar()
    }

    @SuppressLint("StaticFieldLeak")
    private inner class PlacesTask : AsyncTask<String?, Int?, String?>() {
        override fun doInBackground(vararg params: String?): String? {
            var data: String? = null
            try {
                data = downloadUrl(params[0].toString())
            } catch (e: Exception) {
                bind.progressBar.visibility = View.GONE
                e.printStackTrace()
            }
            return data
        }

    }

    private fun downloadUrl(strUrl: String): String { //menghubungkan koneksi aplikasi android ke api
        var data = ""
        val iStream: InputStream
        val urlConnection: HttpURLConnection
        try {
            val url = URL(strUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream))
            val sb = StringBuilder()
            var line: String?
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            data = sb.toString()
            br.close()
            iStream.close()
            urlConnection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }


    @SuppressLint("StaticFieldLeak")
    private inner class ParserTask : AsyncTask<String?, Int?, List<HashMap<String, String>>?>() {
        var jObject: JSONObject? = null
        override fun doInBackground(vararg jsonData: String?): List<HashMap<String, String>>? {
            var places: List<HashMap<String, String>>? = null
            var parsePlace = ParserPlace()
            try {
                jObject = JSONObject(jsonData[0])
                places = parsePlace.parse(jObject!!)
            } catch (e: Exception) {
                bind.progressBar.visibility = View.GONE
                e.printStackTrace()
            }
            return places
        }
        override fun onPostExecute(list: List<HashMap<String, String>>?) { //menampilkan list yang sudah di parse di parser place
            mMap!!.clear()
            for (i in list!!.indices) {
                val markerOption = MarkerOptions()
                val hmPlace = list[i]
                val pinDrop = BitmapDescriptorFactory.fromResource(R.drawable.ic_pn)
                val lat = hmPlace["lat"]!!.toDouble()
                val lng = hmPlace["lng"]!!.toDouble()
                val nama = hmPlace["place_name"]
                val namajalan = hmPlace["vicinity"]
                val latLng = LatLng(lat, lng)
                markerOption.icon(pinDrop)
                markerOption.position(latLng)
                markerOption.title(nama)
                mMap!!.addMarker(markerOption)
            }
            bind.progressBar.visibility = View.GONE
        }
    }



    private fun stopProgressBar() {
        pBar!!.visibility = View.GONE
    }

    private fun runProgressBar() {
        pBar!!.visibility = View.VISIBLE
    }


}


