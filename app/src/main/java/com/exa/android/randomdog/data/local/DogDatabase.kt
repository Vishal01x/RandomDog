package com.exa.android.finddog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DogEntity::class],
    version = 1
)
abstract class DogDatabase : RoomDatabase(){
    abstract val dao : DogDao
}