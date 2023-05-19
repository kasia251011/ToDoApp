package com.example.todoapp.ui.task.common

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.InputStream

@SuppressLint("Recycle")
@Composable
fun FileAdd(task: Task, updateState: (Task) -> Unit) {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    val coroutineScope = rememberCoroutineScope()

    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return@withContext
                    updateState(task.copy(file = inputStream.readBytes()))
                    inputStream.close()
                }
            }
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
        if(task.file?.isNotEmpty() == true) {
            ImageCard(
                task.file ,
                setLoadedFile = {updateState(task.copy(file = null))},
                true)
        }
    }
}
