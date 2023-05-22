package com.dan.qchat.data.remote.api

import com.dan.qchat.domain.model.Message

interface MessageService {

    suspend fun getAll(): List<Message>

    companion object {

        const val BASE_URL = "http://34.79.179.182"
    }

    sealed class EndPoints(val url: String) {

        object GetAllMessages : EndPoints("$BASE_URL/messages")
    }
}