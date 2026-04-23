package com.example.personallifelogger.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.personallifelogger.ui.screens.AddEntryScreen
import com.example.personallifelogger.ui.screens.EntryDetailScreen
import com.example.personallifelogger.ui.screens.HomeScreen
import com.example.personallifelogger.viewmodel.EntryViewModel

object Routes {
    const val HOME = "home"
    const val ADD = "add"
    const val DETAIL = "detail/{entryId}"
    fun detail(id: Long) = "detail/$id"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    // Single shared ViewModel scoped to the activity
    val viewModel: EntryViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate(Routes.ADD) },
                onEntryClick = { id -> navController.navigate(Routes.detail(id)) }
            )
        }
        composable(Routes.ADD) {
            AddEntryScreen(
                viewModel = viewModel,
                onSaved = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStack ->
            val id = backStack.arguments?.getLong("entryId") ?: 0L
            EntryDetailScreen(
                entryId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
