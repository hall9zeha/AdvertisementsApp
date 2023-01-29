package com.barryzeha.bannersapp

import android.app.Application
import com.google.android.gms.ads.MobileAds


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(applicationContext)
    }
}