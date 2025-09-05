package com.example.islamicapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EveningAzkar(navController: NavController) {
    val azkarList = listOf(
        Azkar(
            arabic = "اللّهـمَّ بكَ أمسَيْنا وبكَ أصْبَحْنا، وبكَ نحْيا وبكَ نمُوتُ، وإلَيْكَ المصِير",
            translation = "O Allah, by Your leave we have reached the evening and by Your leave we reach the morning, by Your leave we live and die, and unto You is our return.",
            reference = "Tirmidhi 3391"
        ),
        Azkar(
            arabic = "اللّهـمَّ ما أمسى بي من نعمةٍ أو بأحَدٍ من خَلْقِك، فمِنْكَ وحدَك، لا شريكَ لك، فلكَ الحمدُ ولكَ الشُّكْر",
            translation = "O Allah, whatever blessing has been received by me or anyone of Your creation in the evening is from You alone, You have no partner, so for You is all praise and unto You all thanks.",
            reference = "Abu Dawood 5073"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evening Azkar", color = Color.White) }, navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B1B1B))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(azkarList) { azkar ->
                AzkarCard(azkar)
            }
        }
    }
}
