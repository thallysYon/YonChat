package com.example.yonchat

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth


class MensagemAdapter(val context: Context, val messageList: ArrayList<Mensagem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1;
    val ITEM_SENT = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == 1){

            val view: View = LayoutInflater.from(context).inflate(R.layout.recebidos, parent, false)
            return RecebidasViewHolder(view)

        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.enviados, parent, false)
            return EnviadasViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mensagemAtual = messageList[position]

        if (holder.javaClass == EnviadasViewHolder::class.java){

            //Enviadas view holder

            val viewHolder = holder as EnviadasViewHolder

            holder.mensagensEnviadas.text = mensagemAtual.mensagem

        }else{

            //Recebidas view holder

            val viewHolder = holder as RecebidasViewHolder

            holder.mensagensRecebidas.text = mensagemAtual.mensagem

        }

    }

    override fun getItemViewType(position: Int): Int {
        val mensagemAtual = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(mensagemAtual.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVE
        }
    }

    class EnviadasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mensagensEnviadas = itemView.findViewById<TextView>(R.id.txt_mensagens_enviadas)
    }

    class RecebidasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mensagensRecebidas = itemView.findViewById<TextView>(R.id.txt_mensagens_recebidas)
    }

}