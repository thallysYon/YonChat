package com.example.yonchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CadastroActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnCadastro: Button

    private lateinit var autenticacao: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        autenticacao = FirebaseAuth.getInstance()

        edtNome = findViewById(R.id.edt_nome)
        edtEmail = findViewById(R.id.edit_email)
        edtSenha = findViewById(R.id.edit_senha)
        btnCadastro = findViewById(R.id.btn_cadastro)

        btnCadastro.setOnClickListener{
            val nome = edtNome.text.toString()
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            cadastrar(nome,email, senha)
        }

    }

    private fun cadastrar(nome: String,email: String, senha:String){

        autenticacao.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    cadastrarUsuarioNoBanco(nome, email, autenticacao.currentUser?.uid!!)

                    //Vai pra home
                    val intent = Intent(this@CadastroActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    //Exibe mensagem de erro
                    Toast.makeText(this@CadastroActivity,"Ocorreu um erro ao tentar cadastrar.", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun cadastrarUsuarioNoBanco(nome: String, email: String, uid:String){

        databaseReference = FirebaseDatabase.getInstance().getReference()
        databaseReference.child("usuario").child(uid).setValue(Usuario(nome, email, uid))

    }
}