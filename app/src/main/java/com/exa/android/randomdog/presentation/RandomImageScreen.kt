package com.exa.android.finddog.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.exa.android.finddog.domain.Dog
import com.exa.android.finddog.presentation.component.ButtonComponent
import com.exa.android.finddog.presentation.component.ImageComponent
import com.exa.android.finddog.presentation.viewmodel.DogViewModel
import com.exa.android.finddog.utils.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomImageScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Generate Dogs!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
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
            DogImageGenerator(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
fun DogImageGenerator(modifier: Modifier = Modifier) {
    val viewModel: DogViewModel = hiltViewModel()
    val randomImageResponse by viewModel.dogImage.collectAsState()

    val context = LocalContext.current
    var errorText by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }

    when (val response = randomImageResponse) {
        is Response.Loading -> {
            showLoader = true
            errorText = "" // Clear any existing error while loading
        }

        is Response.Success -> {
            showLoader = false
            errorText = "" // Clear error if request is successful
        }

        is Response.Error -> {
            showLoader = false
            errorText = response.message
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(6f) // Take up available space
                .fillMaxSize()
        ) {
            // If no image is available, show placeholder
            if (randomImageResponse is Response.Success && (randomImageResponse as Response.Success<Dog>).data.imageUrl.isNullOrEmpty()) {
                PlaceholderView()
            }

            // If image is successfully fetched, show the image
            if (randomImageResponse is Response.Success && !(randomImageResponse as Response.Success<Dog>).data.imageUrl.isNullOrEmpty()) {
                (randomImageResponse as Response.Success<Dog>).data.imageUrl?.let {
                    ImageComponent(
                        it
                    )
                }
            }

            // Show loader over the image when it's loading
            if (showLoader) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp))
            }
        }

        // Display error message if any
        if (errorText.isNotEmpty()) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
        }

        // Button to fetch a new image, fixed at the bottom
        Spacer(modifier = Modifier.weight(2f)) // Push the button to the bottom
        ButtonComponent("Generate!") {
            viewModel.fetchRandomDogImage()
        }
    }
}

@Composable
fun PlaceholderView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.4f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Click the button to generate image",
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
