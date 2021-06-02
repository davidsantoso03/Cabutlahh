package com.bangkit.cabutlahapp.data.remote
import android.content.Context
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.retrofit.ApiConfig
import com.bangkit.cabutlahapp.retrofit.MapResponse
import com.bangkit.cabutlahapp.retrofit.ResultsItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class RemoteSource {

    companion object {
        @Volatile
        private var instance: RemoteSource? = null

        fun getInstance(): RemoteSource =
            instance ?: synchronized(this) {
                instance ?: RemoteSource().apply { instance = this }
            }

    }

    private val dbRef = FirebaseDatabase.getInstance()

    fun getNearbyPlaces(
        context: Context,
        type: String,
        location: String,
        output: String,
        key: String
    ): LiveData<MapResponse> {
        val result = MutableLiveData<MapResponse>()

        val responses = ApiConfig.getApiService().getPlaces(output, location, type, 1500, key)
        responses.enqueue(object : Callback<MapResponse> {
            override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                try {
                    if (response.isSuccessful) {
                        result.postValue(response.body())
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "ResponseError: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                Toast.makeText(context, "ResponseFailure: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })

        return result
    }
    fun getHotelList(context: Context): LiveData<ArrayList<Hotel>> {
        val list = MutableLiveData<ArrayList<Hotel>>()
        val result = dbRef.getReference("hotel")

        result.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val item = ArrayList<Hotel>()

                    for (hotelSnapshot in snapshot.children) {
                        val hotel = hotelSnapshot.getValue(Hotel::class.java)
                        item.add(hotel!!)
                    }

                    list.value = item
                } else {
                    Toast.makeText(context, "Tidak ada data hotel", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "onCanceled Hotel: ${error.message}", Toast.LENGTH_LONG)
                        .show()
            }

        })

        return list
    }

    fun getRestoList(context: Context): LiveData<ArrayList<Restaurant>> {
        val list = MutableLiveData<ArrayList<Restaurant>>()
        val result = dbRef.getReference("Restaurant")

        result.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val item = ArrayList<Restaurant>()

                    for (restoSnapshot in snapshot.children) {
                        val resto = restoSnapshot.getValue(Restaurant::class.java)
                        item.add(resto!!)
                    }

                    list.value = item
                } else {
                    Toast.makeText(context, "Tidak ada data restoran", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                        context,
                        "onCanceled Restaurant: ${error.message}",
                        Toast.LENGTH_LONG
                ).show()
            }

        })

        return list
    }

    fun getVacationList(context: Context): LiveData<ArrayList<Vacation>> {
        val list = MutableLiveData<ArrayList<Vacation>>()
        val result = dbRef.getReference("wisata")

        result.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val item = ArrayList<Vacation>()

                    for (vacSnapshot in snapshot.children) {
                        val vac = vacSnapshot.getValue(Vacation::class.java)
                        item.add(vac!!)
                    }

                    list.value = item
                } else {
                    Toast.makeText(context, "Tidak ada data tempat hiburan", Toast.LENGTH_LONG)
                            .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "onCanceled Vacation: ${error.message}", Toast.LENGTH_LONG)
                        .show()
            }

        })

        return list
    }

    fun getHotelDetail(context: Context, title: String): LiveData<Hotel> {
        val result = MutableLiveData<Hotel>()
        val list = dbRef.getReference("hotel")

        list.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (hotSnapshot in snapshot.children){
                        val hotel = hotSnapshot.getValue(Hotel::class.java)

                        if (hotel?.nama == title){
                            result.value = hotel!!
                        }
                    }
                } else {
                    Toast.makeText(context, "Detail tidak ditemukan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "onCanceled hotel detail: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })

        return result
    }

    fun getVacationDetail(context: Context, title: String): LiveData<Vacation> {
        val result = MutableLiveData<Vacation>()
        val list = dbRef.getReference("wisata")

        list.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (vacSnapshot in snapshot.children){
                        val vac = vacSnapshot.getValue(Vacation::class.java)

                        if (vac?.nama == title){
                            result.value = vac!!
                        }
                    }
                } else {
                    Toast.makeText(context, "Detail tidak ditemukan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "onCanceled vacation detail: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })

        return result
    }

    fun getRestoDetail(context: Context, title: String): LiveData<Restaurant> {
        val result = MutableLiveData<Restaurant>()
        val list = dbRef.getReference("Restaurant")

        list.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (resSnapshot in snapshot.children){
                        val resto = resSnapshot.getValue(Restaurant::class.java)

                        if (resto?.nama == title){
                            result.value = resto!!
                        }
                    }
                } else {
                    Toast.makeText(context, "Detail tidak ditemukan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "onCanceled resto detail: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })

        return result
    }
}