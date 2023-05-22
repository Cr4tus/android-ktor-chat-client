package com.dan.qchat.presentation.chat.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dan.qchat.domain.model.Message
import com.dan.qchat.ui.theme.gray
import com.dan.qchat.ui.theme.lightGray

@Composable
fun Message(
    message: Message,
    isMine: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .background(
                    color = if (isMine) gray else Color.Black,
                    shape = RoundedCornerShape(25.dp),
                )
                .border(
                    width = if (isMine) (-1).dp else 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 15.dp
                )
        ) {
            val color = lightGray
            Text(
                text = message.username,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = message.body,
                color = color
            )
            Text(
                text = message.formattedTimestamp,
                color = color,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
