package com.example.yonchat

class Mensagem {

    var mensagem: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(mensagem: String?, senderId: String?){

        this.mensagem = mensagem
        this.senderId = senderId

    }
}