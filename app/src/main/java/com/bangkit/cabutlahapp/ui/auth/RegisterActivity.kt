package com.bangkit.cabutlahapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.cabutlahapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goToLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val userName = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim(' ')
            val password = binding.password.text.toString().trim()
            val confirmPass = binding.confirmPassword.text.toString().trim()
            val phoneNo = binding.phoneNumber.text.toString().trim()

            when {
                userName.isEmpty() -> binding.username.error = "Please Input User Name"
                email.isEmpty() -> binding.email.error = "Please write your email"
                !email.contains("@") -> binding.email.error = "Your email is not exist"
                password.isEmpty() -> binding.password.error = "Please write your password"
                password.length < 8 -> binding.password.error = "Your password must be 8 characters"
                confirmPass.isEmpty() -> binding.confirmPassword.error =
                    "Please fill the confirm pass"
                confirmPass != password -> binding.confirmPassword.error =
                    "Password is not the same as confirm password"
                phoneNo.isEmpty() ->binding.inputPhoneNumber.error = "Please input your number"
                else -> {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Register Succesfull", Toast.LENGTH_LONG)
                                    .show()

                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Register Unsuccessfull : ${task.exception}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }
}