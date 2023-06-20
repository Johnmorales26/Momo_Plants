package com.johndev.momoplantsparent.common.entities

import com.google.firebase.database.Exclude

data class MessageEntity(
    @get:Exclude var id: String = "",
    var message: String = "",
    var sender: String = "",
    var time: String = "",
    @get:Exclude var myUid: String = ""
) {

    @Exclude
    fun isSentByMe(): Boolean = sender == myUid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MessageEntity
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
