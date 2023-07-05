package com.johndev.momoplantsparent.common.utils

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.common.utils.Constants.COLL_PLANTS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_REQUESTS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_TOKENS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_USERS
import javax.inject.Inject

class FirebaseUtils @Inject constructor(
    private var firebaseFirestore: FirebaseFirestore,
    private var firebaseAnalytics: FirebaseAnalytics,
    private val firebaseAuth: FirebaseAuth
) {
    fun getPlantsRef() = firebaseFirestore.collection(COLL_PLANTS)
    fun getRequestsRef() = firebaseFirestore.collection(COLL_REQUESTS)
    fun getUsersRef() = firebaseFirestore.collection(COLL_USERS)
    fun getTokensRef() = firebaseFirestore.collection(COLL_TOKENS)
    fun getAnalyticsLogEvent(pairAction: Pair<String, Long>, pairMethod: Pair<String, String>) =
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.SUCCESS, 100)
            param(FirebaseAnalytics.Param.METHOD, "sign_out")
        }
    fun getCurrentUser() = firebaseAuth.currentUser

}