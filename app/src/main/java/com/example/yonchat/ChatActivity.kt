package com.example.yonchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var caixaDeMensagem: EditText
    private lateinit var botaoEnviar: ImageView
    private lateinit var mensagemAdapter: MensagemAdapter
    private lateinit var listaDeMensagens: ArrayList<Mensagem>
    private lateinit var databaseReference: DatabaseReference


    var enviouSala: String? = null
    var recebeuSala: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val nome = intent.getStringExtra("nome")
        val recebeuUid = intent.getStringExtra("uid")

        val enviouUid = FirebaseAuth.getInstance().currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference()

        enviouSala = recebeuUid + enviouUid
        recebeuSala = enviouUid + recebeuUid


        recebeuSala

        supportActionBar?.title = nome

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        caixaDeMensagem = findViewById(R.id.caixaDeMensagem)
        botaoEnviar = findViewById(R.id.btn_enviar)
        listaDeMensagens = ArrayList()
        mensagemAdapter = MensagemAdapter(this, listaDeMensagens)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = mensagemAdapter


        databaseReference.child("conversas").child(recebeuSala!!).child("mensagens")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    listaDeMensagens.clear()

                    for(postSnapshot in snapshot.children){

                        val mensagem = postSnapshot.getValue(Mensagem::class.java)
                        listaDeMensagens.add(mensagem!!)

                    }
                    mensagemAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        botaoEnviar.setOnClickListener{

            val mensagem = caixaDeMensagem.text.toString()

            val mensagemObject = Mensagem(mensagem, enviouUid)

            databaseReference.child("conversas").child(enviouSala!!).child("mensagens").push()
                .setValue(mensagemObject).addOnCompleteListener{
                    databaseReference.child("conversas").child(recebeuSala!!).child("mensagens").push()
                        .setValue(mensagemObject)
                }
            caixaDeMensagem.setText("")

        }
    }

}