package com.dan.qchat.domain.validation

class UsernameValidator : Validator<String> {

    override fun validate(entity: String) {
        if (entity.isEmpty() || 3 > entity.length) {
            throw IllegalArgumentException("the username's minimum length is 3.")
        }
    }
}