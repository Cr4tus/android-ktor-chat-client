package com.dan.qchat.domain.validation

import java.lang.IllegalArgumentException

class MessageValidator : Validator<String> {

    override fun validate(entity: String) {
        if (entity.isEmpty()) {
            throw IllegalArgumentException("empty message body.")
        }
    }
}