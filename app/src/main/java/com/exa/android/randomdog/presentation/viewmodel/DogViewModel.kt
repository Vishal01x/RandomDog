package com.exa.android.finddog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exa.android.finddog.data.repository.DogRepository
import com.exa.android.finddog.domain.Dog
import com.exa.android.finddog.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val dogRepository: DogRepository
)  :ViewModel(){

    private val _dogImagesList = MutableStateFlow<List<Dog>>(emptyList())
    val dogImagesList : StateFlow<List<Dog>> = _dogImagesList

    private val _dogImage = MutableStateFlow<Response<Dog>>(Response.Success(Dog()))
    val dogImage : StateFlow<Response<Dog>> = _dogImage


    init {
        getAllImages()
    }

    fun fetchRandomDogImage(){
        viewModelScope.launch {
            dogRepository.getRandomDogImage().collect {dogResponse->
                _dogImage.value = dogResponse
            }
        }
    }

    fun getAllImages(){
        viewModelScope.launch {
            dogRepository.getAllDogImages().collect{
                _dogImagesList.value = it
            }
        }
    }

    fun clearCache(){
        viewModelScope.launch {
            dogRepository.clearCache()
        }
    }
}