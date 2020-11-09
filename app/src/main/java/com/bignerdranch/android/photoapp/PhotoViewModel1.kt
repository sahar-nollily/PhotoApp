package com.bignerdranch.android.photoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.photoapp.database.Photo
import com.bignerdranch.android.photoapp.database.PhotoDatabase
import kotlinx.coroutines.launch

class PhotoViewModel1 (application: Application) : AndroidViewModel(application){

    var photos : LiveData<List<Photo>>
    private val photoRepository : PhotoRepository

    init{
        val taskDao = PhotoDatabase.getDatabase(application).photoDao()
        photoRepository = PhotoRepository(taskDao)
        photos = photoRepository.readAllPhoto
    }

     fun addPhoto (photo: Photo){
        viewModelScope.launch {
            photoRepository.addPhotos(photo)
        }
    }
}