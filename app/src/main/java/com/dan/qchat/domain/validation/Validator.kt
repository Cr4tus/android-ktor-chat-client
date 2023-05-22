package com.dan.qchat.domain.validation

interface Validator<T> {

    fun validate(entity: T)
}