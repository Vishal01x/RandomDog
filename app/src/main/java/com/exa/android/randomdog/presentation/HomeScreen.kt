package com.exa.android.finddog.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.exa.android.finddog.presentation.component.ButtonComponent

@Composable
fun HomeScreen(
    navController: NavController,
    onRandomGenerateClick: () -> Unit,
    onRecentlyGeneratedClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Random Dog Generator!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(50.dp))

        ButtonComponent("Generate Dogs!") {
            onRandomGenerateClick()
        }

        Spacer(modifier = Modifier.height(8.dp))

        ButtonComponent("My Recently generated Dogs!") {
            onRecentlyGeneratedClick()
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    AppNavigation()
//}