package com.example.islamicapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RadioScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.background6),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.masjid),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFd4b174))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TabButton("Radio", selected = selectedTab == 0) { selectedTab = 0 }
                TabButton("Reciters", selected = selectedTab == 1) { selectedTab = 1 }
            }

            Spacer(modifier = Modifier.height(20.dp))
            if (selectedTab == 0) {
                ReciterCard("Radio Ibrahim Al-Akdar")
            }
            if (selectedTab == 1) {
                ReciterCard("Radio Ibrahim Al-Akdar")
                ReciterCard("Radio Al-Qaria Yassen", isPlaying = true)
                ReciterCard("Radio Ahmed Al-trabulsi")
            }
        }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor = if (selected) Color(0xFFf5e3c3) else Color.Transparent
    val textColor = if (selected) Color.Black else Color.White

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ReciterCard(name: String, isPlaying: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors =
            CardDefaults.cardColors(containerColor = Color(0xFFd4b174)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
                if (isPlaying) {
                    Icon(Icons.Default.PauseCircleFilled, contentDescription = null, tint = Color.Black, modifier = Modifier.size(36.dp))
                } else {
                    Icon(Icons.Default.PlayCircleFilled, contentDescription = null, tint = Color.Black, modifier = Modifier.size(36.dp))
                }
                Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
            }
        }
    }
}
