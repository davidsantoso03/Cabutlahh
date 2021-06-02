package com.bangkit.cabutlahapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.cabutlahapp.*
import com.bangkit.cabutlahapp.databinding.ActivityHomeBinding
import com.bangkit.cabutlahapp.ui.*
import com.bangkit.cabutlahapp.ui.auth.ProfileActivity
import com.bangkit.cabutlahapp.ui.hotel.HotelActivity
import com.bangkit.cabutlahapp.ui.maps.MapsActivity
import com.bangkit.cabutlahapp.ui.restaurant.RestaurantActivity
import com.bangkit.cabutlahapp.ui.vacation.VacationActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.lang.Math.abs

class HomeActivity : AppCompatActivity()
{
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle

    companion object {
        const val EMAIL = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = mAuth.currentUser?.email
        binding.email.text = email

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        toogle = ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_home-> {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_profile -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_logout ->{
                        val mAuth = FirebaseAuth.getInstance()
                        mAuth.signOut()
                        finish()
                    }

                }
            true
        }

        binding.btnLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.restoBtn.setOnClickListener {
            val intent = Intent(this, RestaurantActivity::class.java)
            startActivity(intent)
        }
        binding.hotelBtn.setOnClickListener {
            val intent = Intent(this, HotelActivity::class.java)
            startActivity(intent)
        }
        binding.vacayBtn.setOnClickListener {
            val intent = Intent(this, VacationActivity::class.java)
            startActivity(intent)
        }
        viewPager2 = binding.viewPagerImageSlider
        val sliderItems: MutableList<Sliderdata> = ArrayList()
        sliderItems.add(Sliderdata(R.drawable.photo1))
        sliderItems.add(Sliderdata(R.drawable.photo2))
        sliderItems.add(Sliderdata(R.drawable.photo3))
        sliderItems.add(Sliderdata(R.drawable.photo4))

        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }




}






