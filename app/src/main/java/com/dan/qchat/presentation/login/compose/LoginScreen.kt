package com.dan.qchat.presentation.login.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dan.qchat.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigate: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onJoinChat.collectLatest { username ->
            navigate("chat_screen/$username")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "QChat",
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.LightGray),
                value = viewModel.username.value,
                placeholder = {
                    Text("Enter your username")
                },
                onValueChange = viewModel::setUsername
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = viewModel::joinChat
            ) {
                Text(
                    text = "Join",
                    color = Color.White
                )
            }
        }
    }
}
