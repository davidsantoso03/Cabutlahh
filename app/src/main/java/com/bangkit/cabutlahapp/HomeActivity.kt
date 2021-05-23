package com.bangkit.cabutlahapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.cabutlahapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Math.abs

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()
    companion object{
        const val EMAIL = "email"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra(EMAIL)

        binding.email.text = email

        binding.logout.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            finish()
        }
        binding.btnLocation.setOnClickListener {
            val intent = Intent(this,MapsActivity::class.java)
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
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
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
        sliderHandler.postDelayed(sliderRunnable,3000)
    }
    }




