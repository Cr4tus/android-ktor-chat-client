package com.dan.qchat.data.remote.api

import com.dan.qchat.data.remote.dto.MessageDto
import com.dan.qchat.domain.model.Message
import com.dan.qchat.domain.util.Resource
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            this.socket = this.client.webSocketSession {
                this.url("${ChatSocketService.EndPoints.ChatSocket.url}?username=$username")
            }

            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error("couldn't establish the chat socket connection.")
            }

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "unknown error!")
        }
    }

    override suspend fun closeSession() {
        this.socket?.close()
    }

    override suspend fun sendMessage(messageBody: String) {
        try {
            this.socket?.send(Frame.Text(messageBody))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            this.socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val jsonString = (it as Frame.Text).readText()
                    val messageDto = Json.decodeFromString<MessageDto>(jsonString)
                    messageDto.toMessage()
                } ?: flow { /*empty flow*/ }
        } catch (e: Exception) {
            flow { /*empty flow*/ }
        }
    }
}