package com.example.todoapp.ui.task.common

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@SuppressLint("Recycle")
@Composable
fun FileAdd(task: Task, updateState: (Task) -> Unit) {
    var attachment by remember { mutableStateOf(Uri.EMPTY) }

    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null) {
           attachment = imageUri
        }
    }

    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Icon(
                    painter = painterResource(R.drawable.attachemnt),
                    "attachment",
                    tint = Black,
                    modifier = Modifier.size(25.dp)
                )
                Text("Attachments")
            }

            IconButton({
                pickPictureLauncher.launch("image/*")
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add file"
                )
            }
        }
        if(attachment != Uri.EMPTY) {
            ImageCard(attachment,setAttachment = {attachment = it})
        }

    }


}


@Composable
fun ImageCard(attachment: Uri, setAttachment: (Uri) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 16.dp)
    ) {
        Box() {
            Image(
                painter = rememberImagePainter(attachment),
                contentDescription = null, // Provide a meaningful description if needed
                contentScale = ContentScale.Crop
            )
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
                    IconButton({setAttachment(Uri.EMPTY)}) {
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