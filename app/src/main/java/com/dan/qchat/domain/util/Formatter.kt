package com.dan.qchat.domain.util

import java.text.DateFormat
import java.util.Date

sealed class Formatter {

    object Date : Formatter() {

        fun format(millis: Long): String = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(
                Date(millis)
            )
    }
}