package com.johndev.momoplantsparent.common.dataAccess

import com.johndev.momoplantsparent.common.entities.MessageEntity

interface OnChatListener {

    fun deleteMessage(messageEntity: MessageEntity)

}