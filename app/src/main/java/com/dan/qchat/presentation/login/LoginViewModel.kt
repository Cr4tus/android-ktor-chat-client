package com.dan.qchat.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dan.qchat.domain.validation.UsernameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val validator = UsernameValidator()

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun setUsername(username: String) {
        this._username.value = username
    }

    fun joinChat() {
        this.viewModelScope.launch {
            try {
                val trimmedUsername = username.value.trim()
                validator.validate(trimmedUsername)
                _onJoinChat.emit(trimmedUsername)
            } catch (e: Exception) {
                // handle exception
            }
        }
    }
}