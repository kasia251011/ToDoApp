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
import com.example.todoapp.R
import com.example.todoapp.ui.home.FiltersState
import com.example.todoapp.ui.theme.Black

@Composable
fun HideNotFinishedTasks(
    filterTasks: (String?, List<String>?, Boolean?) -> Unit,
    filtersState: FiltersState,) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Icon(
                painter = painterResource(R.drawable.hide),
                "hide",
                tint = Black,
                modifier = Modifier.size(25.dp)
            )
            Text("Hide not finished tasks ")
        }

        Switch(
            checked = filtersState.onlyDoneTasksVisible,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue, checkedTrackColor = Color(0xFF71A7E7)),
            onCheckedChange = {
                filterTasks(null, null, it)
            }
        )
    }
}