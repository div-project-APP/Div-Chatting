package com.divchatting

import android.content.Context
import android.widget.Toast

class CameraHelper {
    fun takePhoto(context: Context) {
        Toast.makeText(
            context,
            "Camera capture belum diaktifkan pada build ini.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
