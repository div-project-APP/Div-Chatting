package com.divchatting

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class CameraHelper {
    fun takePhoto(context: Context){
        // TODO: implement Camera2 API capture
        val fileUri: Uri = Uri.fromFile(File(Environment.getExternalStorageDirectory(), "photo.jpg"))
        FirebaseStorage.getInstance().reference.child("photos/photo.jpg").putFile(fileUri)
    }
}
