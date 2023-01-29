package com.barryzeha.bannersapp.common.dataAccess

import com.barryzeha.bannersapp.common.Constants
import com.barryzeha.bannersapp.common.entities.RandomImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

class ApiDog {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(RetrofitService::class.java)

    //no hay necesidad de poner return, retrona por defecto
    suspend fun getRandomImages(quantity:Int):RandomImage = withContext(Dispatchers.IO){
        service.getRandomDogList(quantity)
    }


}