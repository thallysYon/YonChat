package com.example.yonchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonDisposableHandle.parent

class UsuarioAdapter(val context:Context, val userList: ArrayList<Usuario>): RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {

        val usuarioAtual = userList[position]

        holder.txtNome.text = usuarioAtual.nome

        holder.itemView.setOnClickListener{

            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("nome", usuarioAtual.nome)
            intent.putExtra("uid", usuarioAtual.uid)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UsuarioViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val txtNome = itemView.findViewById<TextView>(R.id.txt_nome)
    }

}
