package com.example.islamicapp.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }
    val context = LocalContext.current
    val preferenceManager = remember { PreferenceManager(context) }

    val isOnBoardingCompleted by preferenceManager.isOnBoardingCompleted.collectAsState(initial = false)

    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )

        delay(500)

        if (isOnBoardingCompleted) {
            navController.navigate(Screens.QuranScreen.route) {
                popUpTo(Screens.SplashScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.OnBoardingScreen.route) {
                popUpTo(Screens.SplashScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


