package com.johndev.momoplantsparent

import android.app.Application
import com.johndev.momoplantsparent.common.fmc.VolleyHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MomoParentApp : Application() {

    companion object {
        lateinit var volleyHelper: VolleyHelper
    }

    override fun onCreate() {
        super.onCreate()
        volleyHelper = VolleyHelper.getInstance(this)
    }

}