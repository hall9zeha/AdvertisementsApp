package com.barryzeha.bannersapp.common.dataAccess

import com.barryzeha.bannersapp.common.Constants
import com.barryzeha.bannersapp.common.entities.RandomImage
import retrofit2.http.GET
import retrofit2.http.Path


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

interface RetrofitService {
    @GET("api/breeds/image/random/{quantity}")
    suspend fun getRandomDogList(
        @Path("quantity")quantity:Int
    ):RandomImage
}