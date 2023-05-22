package com.dan.qchat.data.remote.api

import com.dan.qchat.data.remote.dto.MessageDto
import com.dan.qchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageServiceImpl(
    private val client: HttpClient
) : MessageService {

    override suspend fun getAll(): List<Message> {
        return try {
            this.client.get<List<MessageDto>>(MessageService.EndPoints.GetAllMessages.url)
                .map { it.toMessage() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}