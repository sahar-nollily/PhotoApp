package com.bignerdranch.android.photoapp

import androidx.lifecycle.LiveData
import com.bignerdranch.android.photoapp.database.Photo
import com.bignerdranch.android.photoapp.database.PhotoDao


class PhotoRepository (private val photoDao: PhotoDao){

    val readAllPhoto: LiveData<List<Photo>> = photoDao.readAllPhotos()

    suspend fun addPhotos(photo: Photo){
        photoDao.addPhoto(photo)
    }
}