package com.example.islamicapp.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(navController)
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController)
        }






    }
}


sealed class Screens(
    val route: String,
    val title: String,
    val Icon: ImageVector,

    ) {
    object SplashScreen : Screens(
        "SplashScreen",
        "SplashScreen",
        Icon = Icons.Filled.Add,
    )
    object OnBoardingScreen : Screens(
        "OnBoardingScreen",
        "OnBoardingScreen",
        Icon = Icons.Filled.Add,
    )
    object HomeScreen : Screens(
        "HomeScreen",
        "HomeScreen",
        Icon = Icons.Filled.Add,
    )




}