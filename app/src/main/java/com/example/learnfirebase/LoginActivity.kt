package com.example.learnfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfirebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding=ActivityLoginBinding.inflate(layoutInflater)
        val view=loginBinding.root
        setContentView(view)
        loginBinding.btnLogin.setOnClickListener {

        }
        loginBinding.btnRegister.setOnClickListener {
            val intent= Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}