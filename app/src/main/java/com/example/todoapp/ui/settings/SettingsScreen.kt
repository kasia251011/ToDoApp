package com.example.todoapp.ui.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.ToDoTopAppBar
import com.example.todoapp.ui.home.HomeViewModel
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.theme.Black
import com.example.todoapp.ui.theme.LightGrey
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.example.todoapp.Category
import com.example.todoapp.ui.home.FiltersState
import com.example.todoapp.ui.settings.components.HideNotFinishedTasks
import com.example.todoapp.ui.settings.components.VisibleCategoryToggle

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val title = "Settings"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    viewModel: HomeViewModel
) {
    Scaffold(
        topBar = { ToDoTopAppBar(
            SettingsDestination.title,
            canNavigateBack = true,
            navigateBack = navigateBack) },
    ) { innerPadding ->
        SettingsBody(
            modifier = Modifier.padding(innerPadding),
            filterTasks = viewModel::filterTasks,
            filtersState = viewModel.filtersState
        )
    }
}

@Composable
fun SettingsBody (
    modifier: Modifier = Modifier,
    filterTasks: (String?, List<String>?, Boolean?) -> Unit,
    filtersState: FiltersState,
) {
    Column {
        HideNotFinishedTasks(filterTasks, filtersState)
        Divider(color = LightGrey)
//        Notifications()
//        Divider(color = LightGrey)
        Column() {
            Text(
                "Visible categories",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp))
            VisibleCategoryToggle(Category.Office, filterTasks, filtersState)
            VisibleCategoryToggle(Category.School, filterTasks, filtersState)
            VisibleCategoryToggle(Category.Home, filterTasks, filtersState)
            VisibleCategoryToggle(Category.Other, filterTasks, filtersState)
        }
    }
}


