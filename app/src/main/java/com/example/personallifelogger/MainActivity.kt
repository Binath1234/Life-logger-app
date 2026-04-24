package com.example.personallifelogger

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personallifelogger.navigation.AppNavGraph
import com.example.personallifelogger.ui.screens.LoginScreen
import com.example.personallifelogger.ui.theme.PersonalLifeLoggerTheme
import com.example.personallifelogger.viewmodel.AuthViewModel
import com.example.personallifelogger.viewmodel.AuthState
import com.example.personallifelogger.viewmodel.AuthViewModelFactory
import com.example.personallifelogger.viewmodel.EntryViewModel
import com.example.personallifelogger.viewmodel.EntryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalLifeLoggerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppWithAuth()
                }
            }
        }
    }
}

@Composable
fun AppWithAuth() {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(application)
    )

    val entryViewModel: EntryViewModel = viewModel(
        factory = EntryViewModelFactory(application)
    )

    val authState by authViewModel.authState.collectAsState()
    var isLoggedIn by remember { mutableStateOf(false) }

    when (authState) {
        is AuthState.Authenticated -> {
            if (!isLoggedIn) {
                isLoggedIn = true
            }
            AppNavGraph(
                entryViewModel = entryViewModel,
                onLogout = {
                    authViewModel.signOut()
                    isLoggedIn = false
                }
            )
        }
        is AuthState.Unauthenticated -> {
            isLoggedIn = false
            LoginScreen(
                onLoginSuccess = {
                    // Login successful - state will update automatically
                }
            )
        }
        is AuthState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}