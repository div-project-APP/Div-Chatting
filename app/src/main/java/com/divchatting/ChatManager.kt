package com.divchatting

import com.google.firebase.firestore.FirebaseFirestore

class ChatManager(private val myId: String) {
    private val db = FirebaseFirestore.getInstance()

    fun sendMessage(targetId: String, text: String) {
        val message = mapOf(
            "sender" to myId,
            "receiver" to targetId,
            "message" to text,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("chats").add(message)
    }
}
