package com.exa.android.finddog.data.repository

import android.util.Log
import androidx.compose.foundation.text.selection.DisableSelection
import com.exa.android.finddog.data.local.DogDatabase
import com.exa.android.finddog.data.local.DogEntity
import com.exa.android.finddog.data.mappers.toDog
import com.exa.android.finddog.data.mappers.toDogEntity
import com.exa.android.finddog.data.mappers.toDogList
import com.exa.android.finddog.data.remote.DogApi
import com.exa.android.finddog.domain.Dog
import com.exa.android.finddog.utils.Constants
import com.exa.android.finddog.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val dogApi: DogApi,
    private val dogDb: DogDatabase
) {

    private val maxSize = Constants.maxSize

    fun getRandomDogImage(): Flow<Response<Dog>> = flow {
        try {
            emit(Response.Loading)
            val response = dogApi.getRandomImage()
            if (response.isSuccessful) {
                response.body()?.let { dogDto ->
                    if(dogDto.message != null) {
                        dogDto.message.let { Log.d("repositoryDog", it) }
                        val dogEntity = dogDto.toDogEntity()
                        insertInDB(dogEntity)
                        val dog = dogEntity.toDog()
                        emit(Response.Success(dog))
                    }
                } ?: emit(Response.Error("No data received"))
            } else {
                emit(Response.Error("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Response.Error("Network Error: ${e.localizedMessage}"))
        }
    }

    // add new dogImage in DB and maintain cache size ie 20
    private suspend fun insertInDB(dogEntity: DogEntity) {
        dogDb.dao.insertDog(dogEntity)
        dogDb.dao.maintainCacheSize(maxSize)
    }

    // get all dog images following lru cache
    suspend fun getAllDogImages(): Flow<List<Dog>> {
        return withContext(Dispatchers.IO) {
            dogDb.dao.getRecentDogImages(maxSize).map {
                it.toDogList()
            }
        }
    }

    // clear cache
    suspend fun clearCache(){
        withContext(Dispatchers.IO){
            dogDb.dao.clearCache()
        }
    }
}