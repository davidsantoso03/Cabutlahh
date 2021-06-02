package com.bangkit.cabutlahapp.ui.auth

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.bangkit.cabutlahapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream


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

//        val imgProfile = binding.imgProfile
//        binding.btnChangeProfpic.setOnClickListener {
//            val openGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(openGallery, 1000)
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1000 && resultCode == RESULT_OK){
//            val imgBitmap = data?.extras?.get("data") as Bitmap
//            uploadImage(imgBitmap)
//        }
//    }
//
//    private fun uploadImage(imgBitmap: Bitmap) {
//        val baos = ByteArrayOutputStream()
//        val ref = FirebaseStorage.get
//    }
}