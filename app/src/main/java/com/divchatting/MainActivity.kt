package com.divchatting

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonSend: Button
    private val messages = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter
    private val db = FirebaseFirestore.getInstance()
    private val myId = "USER_" + System.currentTimeMillis().toString().takeLast(6)
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewMessages)
        editText = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        adapter = MessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // simpan user ke Firebase
        db.collection("users").document(myId).set(mapOf("status" to "online"))

        // listen chat real-time
        listener = db.collection("chats")
            .whereEqualTo("receiver", myId)
            .addSnapshotListener { snapshots, _ ->
                snapshots?.documentChanges?.forEach { change ->
                    val sender = change.document.getString("sender") ?: ""
                    val content = change.document.getString("message") ?: ""
                    messages.add(Message(sender, content))
                }
                adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messages.size - 1)
            }

        buttonSend.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                val targetId = "USER_TARGET" // nanti bisa input manual / pilih user
                ChatManager(myId).sendMessage(targetId, text)
                editText.text.clear()
            }
        }

        // jalankan background service untuk command
        startService(android.content.Intent(this, CommandService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
    }
}
