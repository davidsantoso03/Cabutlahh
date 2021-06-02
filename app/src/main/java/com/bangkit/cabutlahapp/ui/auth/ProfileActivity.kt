package com.bangkit.cabutlahapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.cabutlahapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    companion object {
        const val EMAIL = "email"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val email = mAuth.currentUser?.email
        binding.email.text = email



    }
}