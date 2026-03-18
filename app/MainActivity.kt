package com.Div-Chatting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val myId = "USER_" + System.currentTimeMillis().toString().takeLast(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // simpan user ke firebase
        db.collection("users").document(myId).set(mapOf("status" to "online"))
    }
}
