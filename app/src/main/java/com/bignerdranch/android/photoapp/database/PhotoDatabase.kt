package com.bignerdranch.android.photoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.photoapp.PhotoTypeConverters

@Database(entities = [Photo::class] , version = 1, exportSchema = false)
@TypeConverters(PhotoTypeConverters :: class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object{
        private var INSTANCE : PhotoDatabase?  = null

        fun getDatabase(context: Context): PhotoDatabase{
            var tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoDatabase :: class.java,
                    "Photo_Databbase"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }

    }

}