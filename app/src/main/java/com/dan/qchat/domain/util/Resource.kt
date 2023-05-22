package com.dan.qchat.domain.util

sealed class Resource<T>(
    val data: T? = null
) {
    class Success<T>(data: T?): Resource<T>(data)
    data class Error<T>(val message: String? = null): Resource<T>()
}