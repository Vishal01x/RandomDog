package com.exa.android.finddog.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.exa.android.finddog.domain.Dog
import com.exa.android.finddog.presentation.component.ButtonComponent
import com.exa.android.finddog.presentation.component.ImageComponent
import com.exa.android.finddog.presentation.viewmodel.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentGeneratedImageScreen(navController: NavController) {

    val viewModel : DogViewModel = hiltViewModel()

    val dogImagesList by viewModel.dogImagesList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Recent generated Dogs!",
                        style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .fillMaxSize()
                    .padding(vertical = 40.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GeneratedImageList(dogImagesList)

                Spacer(modifier = Modifier.height(50.dp))

                // Button to clear cache
                ButtonComponent("Clear Dogs!") {
                    viewModel.clearCache()
                }
            }
        }
    )
}


@Composable
fun GeneratedImageList(dogImagesList : List<Dog>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Space between items
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        if(dogImagesList.isEmpty()){
            item {
                Text("Generate Some More Images", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }
        }

        items(dogImagesList) { dog ->
            dog.imageUrl?.let { ImageComponent(imageUrl = it, modifier = Modifier.size(300.dp)) }
        }
    }
}