package com.example.todoapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.home.HomeDestination
import com.example.todoapp.ui.home.HomeScreen
import com.example.todoapp.ui.task.add.AddTaskDestination
import com.example.todoapp.ui.task.add.AddTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ToDoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToTaskEntry = { },
                navigateToAddTask = {navController.navigate(AddTaskDestination.route)}
            )
        }
        composable(route = AddTaskDestination.route) {
            AddTaskScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}