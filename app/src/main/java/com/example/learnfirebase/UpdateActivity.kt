package com.example.learnfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.learnfirebase.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    lateinit var updateBinding: ActivityUpdateBinding
    val database:FirebaseDatabase=FirebaseDatabase.getInstance()
    val reference:DatabaseReference=database.reference.child("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding= ActivityUpdateBinding.inflate(layoutInflater)
        val view=updateBinding.root
        setContentView(view)
        getAndSetData()
        updateBinding.button.setOnClickListener {
            updateData()
        }
    }
    fun getAndSetData(){
        val name=intent.getStringExtra("name")
        val age=intent.getIntExtra("age",0).toString()
        val email=intent.getStringExtra("email")
        updateBinding.editTextNameUpdate.setText(name)
        updateBinding.editTextAgeUpdate.setText(age)
        updateBinding.editTextEmailUpdate.setText(email)

    }
    fun updateData(){
        val updateName=updateBinding.editTextNameUpdate.text.toString()
        val updateAge=updateBinding.editTextAgeUpdate.text.toString().toInt()
        val updateEmail=updateBinding.editTextNameUpdate.text.toString()
        val userId=intent.getStringExtra("id").toString()
        val userMap= mutableMapOf<String,Any>()
        userMap["userId"]=userId
        userMap["userName"]=updateName
        userMap["userAge"]=updateAge
        userMap["userEmail"]=updateEmail
        reference.child(userId).updateChildren(userMap).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"the user is updated successfully",Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }
}