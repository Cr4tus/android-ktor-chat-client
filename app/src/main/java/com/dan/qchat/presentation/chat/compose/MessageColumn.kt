package com.dan.qchat.presentation.chat.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dan.qchat.domain.model.Message

@Composable
fun MessageColumn(
    modifier: Modifier,
    username: String?,
    messages: List<Message>
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        items(messages) { message ->
            Message(
                message = message,
                isMine = username == message.username
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
