package com.example.bimber.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bimber.data.model.Gif
import com.example.bimber.data.model.serializeGif
import com.example.bimber.data.util.Resource
import com.example.bimber.ui.shared.GifImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory())
) {
    var query by remember { mutableStateOf("") }

    val searchResults by searchViewModel.searchResults
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = { androidx.compose.material.Text("Search GIFs") },
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide() // Close the keyboard on tap
                })
            }) {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        searchViewModel.searchGifs(it)
                    },
                    placeholder = { Text("Type something...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                when (searchResults) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if(query.isNotEmpty()) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is Resource.Success<*> -> {
                        val items = searchResults.data ?: emptyList()
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(items.size) { index ->
                                val gif = items[index]
                                GifImage(
                                    gif.images.original.url,
                                    Modifier
                                        .height(160.dp)
                                        .clickable {
                                            val json = serializeGif(gif)
                                            navController.navigate("details/$json")
                                        }
                                )
                            }
                        }
                    }

                    is Resource.Error<*> -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Error: ${searchResults.message}")
                        }
                    }
                }
            }
        }
    )
}
