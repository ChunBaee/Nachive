package com.chuno.nachive.write.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel() {
    val repo = WriteRepository()

    val writePhotoList = MutableLiveData<MutableList<String>>()
    var mutableWritePhotoList = mutableListOf<String>()


    fun getFromPreference(list : MutableList<String>) {
        mutableWritePhotoList = list
        notifyList()
    }

    fun putPhotoList(uri : String) {
        mutableWritePhotoList.add(uri)
        notifyList()
    }
    fun deletePhotoList (position : Int) {
        mutableWritePhotoList.removeAt(position)
        notifyList()
    }
    fun resetList() {
        mutableWritePhotoList = mutableListOf()
        notifyList()
    }

    private fun notifyList() {
        writePhotoList.value = mutableWritePhotoList
    }

    fun uploadToFirebase(userId : String, time : String, content : String) {
        repo.uploadToRTDB(userId, time, content, mutableWritePhotoList)
    }
}