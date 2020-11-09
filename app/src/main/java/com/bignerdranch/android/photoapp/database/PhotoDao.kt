package com.bignerdranch.android.photoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao{

    @Query("Select * From Photo")
    fun readAllPhotos():LiveData<List<Photo>>

    @Insert
    suspend fun addPhoto(photo: Photo)
}