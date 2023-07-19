package com.example.yonchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var usuarioRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<Usuario>
    private lateinit var adapter: UsuarioAdapter
    private lateinit var autenticacao: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autenticacao = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UsuarioAdapter(this, userList)

        usuarioRecyclerView = findViewById(R.id.usuarioRecyclerView)

        usuarioRecyclerView.layoutManager = LinearLayoutManager(this)

        usuarioRecyclerView.adapter = adapter

        databaseReference.child("usuario").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnaphot in snapshot.children){

                    val usuarioAtual = postSnaphot.getValue(Usuario::class.java)

                    if(autenticacao.currentUser?.uid != usuarioAtual?.uid){
                        userList.add(usuarioAtual!!)
                    }

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {


            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sair){

            autenticacao.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)

            finish()

            startActivity(intent)

            return true

        }else{

        }

        return true
    }

}