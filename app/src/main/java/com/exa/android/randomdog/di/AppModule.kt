package com.exa.android.finddog.di

import android.content.Context
import androidx.room.Room
import com.exa.android.finddog.data.local.DogDatabase
import com.exa.android.finddog.data.remote.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDogDatabase(@ApplicationContext context: Context): DogDatabase{
        return Room.databaseBuilder(
            context,
            DogDatabase::class.java,
            "DogImageCache"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDogApi() : DogApi{
        return Retrofit.Builder()
            .baseUrl(DogApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApi::class.java)
    }
}