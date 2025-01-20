package com.exa.android.finddog.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface DogApi {

    @GET("breeds/image/random")
    suspend fun getRandomImage() : Response<DogDto>

    companion object{
        val BASE_URL: String
            get() = "https://dog.ceo/api/"
    }


}