package com.chuno.nachive.write.util

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class WriteRepository {

    fun uploadToRTDB(userId : String, time : String, content : String, imgList : MutableList<String>) {
        Log.d("----", "uploadToRTDB: STARTED")
        val contentMap = mutableMapOf<String, Any>()

        contentMap["UploadTime"] = time
        contentMap["Content"] = content
        contentMap["ImageList"] = imgList

        //image올린 후 해당 url 받아오기

        FirebaseDatabase.getInstance().reference.child(userId).child(time).setValue(contentMap)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    Log.d("----", "uploadToRTDB: SUCCESS")
                }
            }.addOnFailureListener {
                Log.d("----", "uploadToRTDB: $it")

            }
    }
}