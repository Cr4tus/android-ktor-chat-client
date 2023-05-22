package com.dan.qchat.domain.model

data class Chat(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
