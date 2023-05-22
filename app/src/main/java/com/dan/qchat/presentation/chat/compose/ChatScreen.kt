package com.dan.qchat.presentation.chat.compose

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dan.qchat.presentation.chat.ChatViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    username: String?,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.errorEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> viewModel.connect()
                Lifecycle.Event.ON_STOP -> viewModel.disconnect()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val chat = viewModel.chat.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MessageColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            username = username,
            messages = chat.messages
        )
        Spacer(modifier = Modifier.height(8.dp))
        BottomChatBar(
            modifier = Modifier.fillMaxWidth(),
            text = viewModel.messageBody.value,
            onTextChangeListener = viewModel::setMessageBody,
            onClickListener = viewModel::sendMessage
        )
    }
}
