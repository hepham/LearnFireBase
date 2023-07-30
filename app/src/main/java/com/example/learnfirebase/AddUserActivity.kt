package com.example.learnfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.learnfirebase.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference =database.reference.child("Users")
    lateinit var addUserBinding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUserBinding= ActivityAddUserBinding.inflate(layoutInflater)
        val view=addUserBinding.root
        setContentView(view)
        addUserBinding.button.setOnClickListener {
            addUserToDatabase()

        }

    }
    fun addUserToDatabase(){
        val name:String=addUserBinding.editTextName.text.toString()
        val age:Int=addUserBinding.editTextAge.text.toString().toInt()
        val email:String=addUserBinding.editTextEmail.text.toString()
        val id:String= reference.push().key.toString()
        val user=User(id,name,age,email)
        reference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,
                    "The new user has been added to the database",
                    Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(applicationContext,
                    task.exception.toString(),
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}