package com.example.todoapp.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontStyle
import com.example.todoapp.ui.theme.Grey

@Composable
fun SearchBar (filterTasks: (String?, List<String>?, Boolean?) -> Unit) {
    var value by remember { mutableStateOf("") }

    BasicTextField(
        value = value ,
        onValueChange = {
            filterTasks(it, null, null)
            value = it
                        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color.White, shape = RoundedCornerShape(40.dp))
            .padding(16.dp),
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                    tint = Grey,
                )
                Box {
                    if(value.isEmpty()) {
                        Text(
                            text = "Search",
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}