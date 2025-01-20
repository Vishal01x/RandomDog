package com.exa.android.finddog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)//whenever new entry of same image will occur it will replace it
    suspend fun insertDog(dog : DogEntity)

//
//    @Query("SELECT * FROM dog_image_cache")
//     fun getAll() : Flow<List<DogEntity>>
//
//
//    @Query("DELETE FROM dog_image_cache")
//     fun clearDB()


    @Query("SELECT * FROM dog_image_cache ORDER BY timestamp DESC LIMIT :limit")
     fun getRecentDogImages(limit: Int): Flow<List<DogEntity>>

    @Query("DELETE FROM dog_image_cache WHERE imageUrl NOT IN (SELECT imageUrl FROM dog_image_cache ORDER BY timestamp DESC LIMIT :limit)")
    suspend fun maintainCacheSize(limit: Int)

    @Query("DELETE  FROM dog_image_cache")
    suspend fun clearCache()

}