package com.exa.android.finddog.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "dog_image_cache")
data class DogEntity(
    @PrimaryKey
    val imageUrl: String,
    val timeStamp : Long = System.currentTimeMillis()
)
