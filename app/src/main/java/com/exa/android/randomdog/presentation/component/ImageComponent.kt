package com.exa.android.finddog.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageComponent(
    imageUrl: String,
    modifier: Modifier = Modifier
        .size(400.dp)
        .padding(16.dp)
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Dog Image",
        modifier = modifier,
        contentScale = ContentScale.Crop

    )
}