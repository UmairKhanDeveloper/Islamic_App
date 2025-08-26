package com.example.islamicapp.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.islamicapp.R

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(navController)
        }
        composable(Screens.QuranScreen.route) {
            QuranScreen(navController)
        }
        composable(Screens.HadethScreen.route) {
            HadethScreen(navController)
        }
        composable(Screens.SebhaScreen.route) {
            SebhaScreen(navController)
        }
        composable(Screens.RadioScreen.route) {
            RadioScreen(navController)
        }
        composable(Screens.TimeScreen.route) {
            TimeScreen(navController)
        }
        composable(Screens.LanguageScreen.route) {
            LanguageScreen(navController)
        }


    }
}


sealed class Screens(
    val route: String,
    val title: String,
    val Icon: Int,

    ) {
    object SplashScreen : Screens(
        "SplashScreen",
        "SplashScreen",
        Icon = R.drawable.splash,
    )

    object OnBoardingScreen : Screens(
        "OnBoardingScreen",
        "OnBoardingScreen",
        Icon = R.drawable.splash,
    )

    object QuranScreen : Screens(
        "QuranScreen",
        "QuranScreen",
        Icon = R.drawable.quran1,
    )

    object HadethScreen : Screens(
        "HadethScreen",
        "HadethScreen",
        Icon = R.drawable.hadeth,
    )

    object SebhaScreen : Screens(
        "SebhaScreen",
        "SebhaScreen",
        Icon = R.drawable.necklace,
    )

    object RadioScreen : Screens(
        "RadioScreen",
        "RadioScreen",
        Icon = R.drawable.sebha,
    )

    object TimeScreen : Screens(
        "TimeScreen",
        "TimeScreen",
        Icon = R.drawable.time,
    )
  object LanguageScreen : Screens(
        "LanguageScreen",
        "LanguageScreen",
        Icon = R.drawable.time,
    )


}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screens.QuranScreen,
        Screens.HadethScreen,
        Screens.SebhaScreen,
        Screens.RadioScreen,
        Screens.TimeScreen
    )

    val navStack by navController.currentBackStackEntryAsState()
    val currentRoute = navStack?.destination?.route

    NavigationBar(
        containerColor = Color(0xffE2BE7F),
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {

                    Icon(
                        painter = painterResource(id = screen.Icon),
                        contentDescription = screen.title,
                        tint = if (isSelected) Color.White else Color(0xff202020),
                        modifier = Modifier.size(24.dp)
                    )

                },
                label = {
                    if (isSelected) {
                        Text(
                            text = screen.title.replace("Screen", ""),
                            color = if (isSelected) Color.White else Color.Gray,
                            fontSize = 10.sp
                        )
                    }

                }, colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF202020).copy(alpha = 0.60f)
                )
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavEntry() {
    val navController = rememberNavController()
    var showBottomNav by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    showBottomNav = when {
        currentRoute == null -> true
        currentRoute.contains(Screens.SplashScreen.route) -> false
        currentRoute.contains(Screens.OnBoardingScreen.route) -> false
        currentRoute.contains(Screens.LanguageScreen.route) -> false


        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        Navigation(navController)
    }
}

