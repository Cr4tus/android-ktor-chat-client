package com.dan.qchat.data.remote.api

import com.dan.qchat.domain.model.Message
import com.dan.qchat.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(username: String): Resource<Unit>

    suspend fun closeSession()

    suspend fun sendMessage(messageBody: String)

    fun observeMessages(): Flow<Message>

    companion object {

        const val BASE_URL = "ws://34.79.179.182"
    }

    sealed class EndPoints(val url: String) {

        object ChatSocket : EndPoints("$BASE_URL/chat-socket")
    }
}