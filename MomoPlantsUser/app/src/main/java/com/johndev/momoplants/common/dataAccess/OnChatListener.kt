package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.MessageEntity

interface OnChatListener {

    fun deleteMessage(messageEntity: MessageEntity)

}