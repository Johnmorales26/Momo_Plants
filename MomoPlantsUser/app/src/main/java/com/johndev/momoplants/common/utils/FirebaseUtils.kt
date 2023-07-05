package com.johndev.momoplants.common.utils

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.utils.Constants.COLL_PLANTS
import com.johndev.momoplants.common.utils.Constants.COLL_REQUESTS
import javax.inject.Inject

class FirebaseUtils @Inject constructor(
    private val database: FirebaseFirestore,
    private val analytics: FirebaseAnalytics
) {

    fun getPlantsRef() = database.collection(COLL_PLANTS)

    fun getRequestsRef() = database.collection(COLL_REQUESTS)

}