package com.example.personallifelogger.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.personallifelogger.R
import com.example.personallifelogger.ui.screens.AddEntryScreen
import com.example.personallifelogger.ui.screens.EntryDetailScreen
import com.example.personallifelogger.ui.screens.HomeScreen
import com.example.personallifelogger.viewmodel.EntryViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddEntry : Screen("add_entry")
    object EntryDetail : Screen("entry_detail/{entryId}") {
        fun passId(id: Long): String = "entry_detail/$id"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    entryViewModel: EntryViewModel,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) },
                        actions = {
                            IconButton(onClick = onLogout) {
                                Icon(Icons.Default.Logout, contentDescription = "Logout")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                HomeScreen(
                    viewModel = entryViewModel,
                    onAddClick = { navController.navigate(Screen.AddEntry.route) },
                    onEntryClick = { entryId ->
                        navController.navigate(Screen.EntryDetail.passId(entryId))
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        composable(Screen.AddEntry.route) {
            AddEntryScreen(
                viewModel = entryViewModel,
                onSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EntryDetail.route,
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getLong("entryId") ?: 0L
            EntryDetailScreen(
                entryId = entryId,
                viewModel = entryViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}