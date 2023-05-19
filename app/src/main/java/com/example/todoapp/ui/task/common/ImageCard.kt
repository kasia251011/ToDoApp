package com.example.todoapp.ui.task.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.convertImageByteArrayToBitmap
import com.example.todoapp.R

@Composable
fun ImageCard(loadedFile: ByteArray, setLoadedFile: (ByteArray?) -> Unit = {}, isEditable: Boolean = false) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 16.dp)
    ) {
        Box {
            println(loadedFile.isEmpty());
            val bitmap = convertImageByteArrayToBitmap(loadedFile)?.asImageBitmap()
            if(bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = null, // Provide a meaningful description if needed
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.broken_image),
                    contentDescription = "broken image"
                )
            }
            if(isEditable) {
                Box(contentAlignment = Alignment.BottomStart,
                    modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = 0.5F))
                            .fillMaxWidth()
                            .height(45.dp)
                    ){}
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp)
                    ) {
                        Text("Forest.png", color = Color.White)
                        IconButton({setLoadedFile(null)}) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Cancel",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}