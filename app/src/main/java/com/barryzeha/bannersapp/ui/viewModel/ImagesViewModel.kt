package com.barryzeha.bannersapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzeha.bannersapp.common.repository.DogImagesRepository
import kotlinx.coroutines.launch


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

class ImagesViewModel:ViewModel() {
    private val repository = DogImagesRepository()

    private val _randomList:MutableLiveData<List<String>> = MutableLiveData()
    fun getRandomList():LiveData<List<String>> = _randomList


    fun callRandomList(quantity:Int){
        viewModelScope.launch {
           val response = repository.getRandomImages(quantity)
            _randomList.postValue(response.message)
        }
    }
}