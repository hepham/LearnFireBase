package com.example.learnfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfirebase.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding=ActivitySignUpBinding.inflate(layoutInflater)
        val view=signUpBinding.root
        setContentView(view)
        signUpBinding.buttonSignUp.setOnClickListener {

        }
    }
}