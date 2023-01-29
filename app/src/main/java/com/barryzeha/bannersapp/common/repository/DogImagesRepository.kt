package com.barryzeha.bannersapp.common.repository

import com.barryzeha.bannersapp.common.dataAccess.ApiDog
import com.barryzeha.bannersapp.common.entities.RandomImage


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

class DogImagesRepository {
    private val apiDog = ApiDog()
    suspend  fun getRandomImages(quantity:Int):RandomImage{
        return apiDog.getRandomImages(quantity)
    }
}