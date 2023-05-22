package com.dan.qchat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dan.qchat.presentation.chat.compose.ChatScreen
import com.dan.qchat.presentation.login.compose.LoginScreen
import com.dan.qchat.ui.theme.QChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QChatTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login_screen"
                ) {
                    composable("login_screen") {
                        LoginScreen(navigate = navController::navigate)
                    }
                    composable(
                        route = "chat_screen/{username}",
                        arguments = listOf(
                            navArgument("username") {
                                this.type = NavType.StringType
                                this.nullable = true
                            }
                        )
                    ) {
                        val username = it.arguments?.getString("username")
                        ChatScreen(username)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}