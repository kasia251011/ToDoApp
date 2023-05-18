package com.example.todoapp.ui.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.Category
import com.example.todoapp.R
import com.example.todoapp.ui.home.FiltersState
import com.example.todoapp.ui.theme.Black
import kotlin.math.log


@Composable
fun VisibleCategoryToggle(
    category: Category,
    filterTasks: (String?, List<String>?, Boolean?) -> Unit,
    filtersState: FiltersState
) {



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Text(category.name)

        Switch(
            checked = filtersState.categories.contains(category.name),
            colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue, checkedTrackColor = Color(0xFF71A7E7)),
            onCheckedChange = {
                if(it) {
                    val newCategories = filtersState.categories.toMutableList()
                    newCategories.add(category.name)

                    println(newCategories)

                    filterTasks(null, newCategories, null)

                } else {
                    var newCategories = filtersState.categories.toMutableList()
                    newCategories = newCategories.filter { cat -> cat != category.name } as MutableList<String>

                    println(newCategories)

                    filterTasks(null, newCategories, null)
                }
            }
        )
    }
}
