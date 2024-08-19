package com.example.bimber.ui.gif_details

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bimber.data.model.Gif
import com.example.bimber.ui.shared.GifImage

@Composable
fun GifDetailsScreen(gif: Gif, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gif.title) },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        // Navigation icon for back action
                        IconButton(onClick = {
                            // Navigate back in the navigation stack
                            navController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    ShareButton(gif.url)
                }
            )
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
            ) {
                GifImage(
                    gif.images.original.url,
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Text(text = "ID: ${gif.id}")
            }
        }
    )
}

@Composable
fun ShareButton(shareUrl: String) {
    val context = LocalContext.current
    IconButton(onClick = {
        shareContent(context, shareUrl)
    }) {
        Icon(Icons.Filled.Share, contentDescription = "Share")
    }
}

fun shareContent(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/uri-list"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}
