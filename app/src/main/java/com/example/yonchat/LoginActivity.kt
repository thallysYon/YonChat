package com.example.yonchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCadastro: Button

    private lateinit var autenticacao: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        autenticacao = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtSenha = findViewById(R.id.edit_senha)
        btnLogin = findViewById(R.id.btn_login)
        btnCadastro = findViewById(R.id.btn_cadastro)

        btnCadastro.setOnClickListener {
            val intent = Intent(this,CadastroActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            login(email,senha)
        }

    }

    private fun login(email: String, senha: String){

        autenticacao.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)

                    finish()

                    startActivity(intent)

                } else {
                    //Exibe uma mensagem de erro
                    Toast.makeText(this@LoginActivity, "Ocorreu um erro ao fazer login.", Toast.LENGTH_SHORT,).show()

                }
            }

    }
}