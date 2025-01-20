package com.exa.android.randomdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exa.android.finddog.presentation.HomeScreen
import com.exa.android.finddog.presentation.RandomImageScreen
import com.exa.android.finddog.presentation.RecentGeneratedImageScreen
import com.exa.android.randomdog.ui.theme.RandomDogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomDogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun App(name : String, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen(
            navController,
            onRandomGenerateClick = {
                navController.navigate("random_image")
            } ,
            onRecentlyGeneratedClick = {
                navController.navigate("generated_images")
            }
        ) }
        composable("random_image") { RandomImageScreen(navController) }
        composable("generated_images") { RecentGeneratedImageScreen(navController) }
    }
}
