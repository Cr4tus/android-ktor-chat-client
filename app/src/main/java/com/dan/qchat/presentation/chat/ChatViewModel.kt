package com.dan.qchat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dan.qchat.data.remote.api.ChatSocketService
import com.dan.qchat.data.remote.api.MessageService
import com.dan.qchat.domain.model.Chat
import com.dan.qchat.domain.model.Message
import com.dan.qchat.domain.util.Resource
import com.dan.qchat.domain.validation.MessageValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val messageValidator = MessageValidator()

    private val _messageBody = mutableStateOf("")
    val messageBody: State<String> = _messageBody

    private val _chat = mutableStateOf(Chat())
    val chat: State<Chat> = _chat

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun setMessageBody(body: String) {
        this._messageBody.value = body
    }

    fun connect(){
        this.getAllMessages()
        this.initConnection()
    }

    private fun getAllMessages() = viewModelScope.launch {
        _chat.value = chat.value.copy(isLoading = true)
        val messages = messageService.getAll()
        _chat.value = chat.value.copy(
            messages = messages,
            isLoading = false
        )
    }

    private fun initConnection() {
        this.savedStateHandle.get<String>("username")?.let {
            this.initConnection(it)
        }
    }

    private fun initConnection(username: String) = viewModelScope.launch {
        when (
            val result = chatSocketService.initSession(username)
        ) {
            is Resource.Success -> {
                observeMessages()
            }
            is Resource.Error -> result.message?.let {
                _errorEvent.emit(it)
            }
        }
    }

    private fun observeMessages() {
        this.chatSocketService.observeMessages()
            .onEach(this::addMessage)
            .launchIn(viewModelScope)
    }

    private fun addMessage(message: Message) {
        val newList = this.chat.value.messages.toMutableList().apply {
            this.add(0, message)
        }
        this._chat.value = this.chat.value.copy(messages = newList)
    }

    fun sendMessage() = viewModelScope.launch {
        try {
            val trimmedBody = messageBody.value.trim()
            messageValidator.validate(trimmedBody)
            chatSocketService.sendMessage(trimmedBody)
            _messageBody.value = ""
        } catch (e: Exception) {
            // in case of empty body message exception
        }
    }

    override fun onCleared() {
        super.onCleared()
        this.disconnect()
    }

    fun disconnect() = viewModelScope.launch {
        chatSocketService.closeSession()
    }
}