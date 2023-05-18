package com.example.todoapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.home.HomeDestination
import com.example.todoapp.ui.home.HomeScreen
import com.example.todoapp.ui.home.HomeViewModel
import com.example.todoapp.ui.settings.SettingsDestination
import com.example.todoapp.ui.settings.SettingsScreen
import com.example.todoapp.ui.task.add.AddTaskDestination
import com.example.todoapp.ui.task.add.AddTaskScreen
import com.example.todoapp.ui.task.details.TaskDetailsDestination
import com.example.todoapp.ui.task.details.TaskDetailsScreen
import com.example.todoapp.ui.task.edit.EditTaskDestination
import com.example.todoapp.ui.task.edit.EditTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ToDoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToTaskDetails = navController::navigate,
                navigateToAddTask = {navController.navigate(AddTaskDestination.route)},
                navigateToSettings = {navController.navigate(SettingsDestination.route)},
                viewModel = homeViewModel
            )
        }
        composable(route = AddTaskDestination.route) {
            AddTaskScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = TaskDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(TaskDetailsDestination.taskIdArg) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
                TaskDetailsScreen(
                navigate = navController::navigate,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = EditTaskDestination.routeWithArgs,
            arguments = listOf(navArgument(EditTaskDestination.taskIdArg) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            EditTaskScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navigateBack = { navController.popBackStack() },
                viewModel = homeViewModel
            )
        }
    }
}