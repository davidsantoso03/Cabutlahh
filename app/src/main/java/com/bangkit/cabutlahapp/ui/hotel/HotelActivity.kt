package com.bangkit.cabutlahapp.ui.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.databinding.ActivityRestaurantBinding
import com.bangkit.cabutlahapp.ui.restaurant.RestaurantAdapter
import com.google.firebase.database.*

class HotelActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var Rv : RecyclerView
    private lateinit var hotelList : ArrayList<Hotel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Rv = findViewById(R.id.hotel_list)
        Rv.layoutManager =  LinearLayoutManager(this)
       Rv.setHasFixedSize(
            true )
        hotelList = arrayListOf<Hotel>()
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        dbRef = FirebaseDatabase.getInstance().getReference("hotel")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (hotelSnapshot in snapshot.children){
                        val hotel = hotelSnapshot.getValue(Hotel::class.java)
                        hotelList.add(hotel!!)
                    }
                    Rv.adapter = HotelAdapter(hotelList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}