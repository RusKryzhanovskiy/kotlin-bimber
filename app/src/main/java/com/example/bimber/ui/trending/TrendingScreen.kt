package com.example.bimber.ui.trending

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bimber.data.model.serializeGif
import com.example.bimber.data.util.Resource
import com.example.bimber.ui.shared.GifImage

@Composable
fun TrendingScreen(
    navController: NavHostController,
    trendingViewModel: TrendingViewModel = viewModel(factory = TrendingViewModelFactory())
) {
    val trendingGifs by trendingViewModel.trendingGifs.collectAsState()

    LaunchedEffect(Unit) {
        trendingViewModel.fetchTrendingGifs()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trending GIFs") },
            )
        },
        content = { paddingValues ->
            when (trendingGifs) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Success -> {
                    val items = trendingGifs.data ?: emptyList()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        items(items.size) { index ->
                            val gif = items[index]
                            GifImage(
                                gif.images.original.url,
                                Modifier
                                    .height(160.dp)
                                    .clickable {
                                        val json = serializeGif(gif);
                                        navController.navigate("details/$json")
                                    },
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${trendingGifs.message}")
                    }
                }
            }
        }
    )
}
