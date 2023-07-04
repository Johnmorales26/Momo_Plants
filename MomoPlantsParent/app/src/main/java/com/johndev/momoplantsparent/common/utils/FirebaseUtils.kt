package com.johndev.momoplantsparent.common.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.common.utils.Constants.COLL_PLANTS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_REQUESTS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_TOKENS
import com.johndev.momoplantsparent.common.utils.Constants.COLL_USERS
import javax.inject.Inject

class FirebaseUtils @Inject constructor(
    private var firebaseFirestore: FirebaseFirestore
) {

    fun getPlantsRef() = firebaseFirestore.collection(COLL_PLANTS)
    fun getRequestsRef() = firebaseFirestore.collection(COLL_REQUESTS)
    fun getUsersRef() = firebaseFirestore.collection(COLL_USERS)
    fun getTokensRef() = firebaseFirestore.collection(COLL_TOKENS)

}