package com.chuno.nachive.write.data

data class UploadToFirebaseDatabase(
    val uploadedTime : String,
    val content : String,
    val images : MutableList<String>

    /*
    bookData, movieData, weather, feeling
     */
)
