package com.johndev.momoplantsparent.common.fmc

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VolleyHelper @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {

        @Volatile
        private var INSTANCE: VolleyHelper? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: VolleyHelper(context).also { INSTANCE = it }
        }

    }

    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(context.applicationContext) }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

}