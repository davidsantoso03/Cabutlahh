package com.bangkit.cabutlahapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.cabutlahapp.databinding.ActivityLoginBinding
import com.bangkit.cabutlahapp.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
   private lateinit var binding: ActivityLoginBinding
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when {
                email.isEmpty() -> binding.email.error = "Please write your email"
                password.isEmpty() -> binding.password.error = "Please write your password"
                else -> {

                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()

                                val intent = Intent(this, HomeActivity::class.java)
                                intent.putExtra(HomeActivity.EMAIL, email)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Login Unsuccessful : ${task.exception}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user = mAuth.currentUser
        if (user != null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.EMAIL, user.email)
            startActivity(intent)
        }

    }
}