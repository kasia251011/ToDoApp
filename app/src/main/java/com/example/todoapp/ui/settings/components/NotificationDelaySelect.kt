package com.example.todoapp.ui.settings.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.Delay
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Black

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationDelaySelect (
    oldDelay: Delay,
    rescheduleNotifications: (Delay) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    val delayOptions = listOf(Delay.MIN1, Delay.MIN3, Delay.MIN5)
    var selectedOptionText by remember { mutableStateOf(oldDelay.label) }

    rescheduleNotifications(oldDelay)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Icon(
                painter = painterResource(R.drawable.hide),
                "hide",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
            Text("Set notification time ")
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            BasicTextField(
                readOnly = true,
                value = selectedOptionText,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onValueChange = { }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },

                ) {
                delayOptions.forEach { delayOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = delayOption.label
                            expanded = false

                            rescheduleNotifications(delayOption)

                        },

                        ) {
                        Text(delayOption.label)
                    }
                }
            }
        }
    }



}