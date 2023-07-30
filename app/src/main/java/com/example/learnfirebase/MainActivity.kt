package com.example.learnfirebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val database:FirebaseDatabase=FirebaseDatabase.getInstance()
    val reference:DatabaseReference=database.reference.child("Users")
    val userList=ArrayList<User>()
    lateinit var userAdapter: UserAdapter
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        val view=mainBinding.root
        setContentView(view)
        retrieveDataFromDatabase()
        mainBinding.floatingActionButton.setOnClickListener {
            val intent=Intent(this@MainActivity,AddUserActivity::class.java)
            startActivity(intent)
        }
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0
            ,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

               val id= userAdapter.getUserId(viewHolder.adapterPosition)
                reference.child(id).removeValue()
                Toast.makeText(applicationContext,"The user was deleted",Toast.LENGTH_LONG).show()
            }

        }).attachToRecyclerView(mainBinding.recyclerView)

    }
    fun retrieveDataFromDatabase(){
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(eacUser in snapshot.children){
                    val user=eacUser.getValue(User::class.java)
                    if(user!=null){
                        userList.add(user)

                    }
                    userAdapter= UserAdapter(this@MainActivity,userList)
                    mainBinding.recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
                    mainBinding.recyclerView.adapter=userAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.deleteAll){
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogMessage() {
        val dialogMessage=AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Users")
        dialogMessage.setMessage("If click Yes,users will be deleted,"+
                "If you want to delete a specific user, you can swipe item you want to delete right or left")
        dialogMessage.setNegativeButton("Cancel",DialogInterface.OnClickListener{dialogInterface, i ->
            dialogInterface.cancel()
        })
        dialogMessage.setPositiveButton("Yes",DialogInterface.OnClickListener{dialogInterface, i ->
            reference.removeValue().addOnCompleteListener{task->
                if(task.isSuccessful){
                    userAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext,"All users were deleted",Toast.LENGTH_LONG).show()
                }
            }
        })
        dialogMessage.create().show()
    }

}