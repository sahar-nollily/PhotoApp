package com.bignerdranch.android.photoapp.database

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity()
data class Photo(@PrimaryKey(autoGenerate = true) var ID:Int=0 ,
                 var photoType : String = "",
                 var photo : Bitmap? = null,
                 var creationDate : Date = Date(),
):Parcelable