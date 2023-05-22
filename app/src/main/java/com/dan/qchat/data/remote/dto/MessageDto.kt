package com.dan.qchat.data.remote.dto

import com.dan.qchat.domain.model.Message
import com.dan.qchat.domain.util.Formatter
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val body: String,
    val username: String,
    val timestamp: Long,
) {

    fun toMessage() = Message(
        body = body,
        username = username,
        formattedTimestamp = Formatter.Date.format(timestamp)
    )
}
