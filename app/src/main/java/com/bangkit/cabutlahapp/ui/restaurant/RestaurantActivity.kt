package com.bangkit.cabutlahapp.ui.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Restaurant
import com.bangkit.cabutlahapp.databinding.ActivityRestaurantBinding
import com.google.firebase.database.*

class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantBinding
    private lateinit var dbRef :DatabaseReference
    private lateinit var restaurantRv : RecyclerView
    private lateinit var restoList : ArrayList<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        restaurantRv = findViewById(R.id.resto_list)
        restaurantRv.layoutManager =  LinearLayoutManager(this)
        restaurantRv.setHasFixedSize(
       true )
        restoList = arrayListOf<Restaurant>()
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        dbRef = FirebaseDatabase.getInstance().getReference("Restaurant")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (restoSnapshot in snapshot.children){
                        val resto = restoSnapshot.getValue(Restaurant::class.java)
                        restoList.add(resto!!)
                    }
                    restaurantRv.adapter = RestaurantAdapter(restoList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}