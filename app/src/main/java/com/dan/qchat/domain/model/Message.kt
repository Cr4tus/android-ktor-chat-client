package com.dan.qchat.domain.model

data class Message(
    val body: String,
    val username: String,
    val formattedTimestamp: String
)
