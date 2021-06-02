package com.bangkit.cabutlahapp.ui.vacation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cabutlahapp.R
import com.bangkit.cabutlahapp.data.model.Hotel
import com.bangkit.cabutlahapp.data.model.Vacation
import com.bangkit.cabutlahapp.ui.hotel.HotelAdapter
import com.bangkit.cabutlahapp.ui.restaurant.VacationAdapter
import com.google.firebase.database.*

class VacationActivity : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference
    private lateinit var Rv : RecyclerView
    private lateinit var vacayList : ArrayList<Vacation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Rv = findViewById(R.id.vacay_list)
        Rv.layoutManager =  LinearLayoutManager(this)
        Rv.setHasFixedSize(
            true )
        vacayList = arrayListOf<Vacation>()
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        dbRef = FirebaseDatabase.getInstance().getReference("wisata")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (vacaySnapshot in snapshot.children){
                        val vacay = vacaySnapshot.getValue(Vacation::class.java)
                        vacayList.add(vacay!!)
                    }
                    Rv.adapter = VacationAdapter(vacayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}