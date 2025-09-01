package com.example.islamicapp.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TimeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.background5),
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
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))
            PrayerTimeUI()

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CardItem(
                    title = "Evening Azkar",
                    image = com.example.islamicapp.R.drawable.evning
                )
                CardItem(
                    title = "Morning Azkar",
                    image = com.example.islamicapp.R.drawable.morinig
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun PrayerTimeUI() {
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(280.dp)
            .clip(RoundedCornerShape(40.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE2BE7F)         )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text("16 Jul,", color = Color.Black, fontSize = 14.sp)
                    Text("2024", color = Color.Black, fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Pray Time",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Tuesday", color = Color.Black, fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("09 Muh,", color = Color.Black, fontSize = 14.sp)
                    Text("1446", color = Color.Black, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrayerTimeItem(title = "Fajr", time = "04:04 AM")
                PrayerTimeItem(title = "Dhuhr", time = "01:01 PM")
                PrayerTimeItem(title = "ASR", time = "04:38 PM", isActive = true)
                PrayerTimeItem(title = "Maghrib", time = "07:57 PM")
                PrayerTimeItem(title = "Isha", time = "09:24 PM")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Next Pray - 02:32",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.VolumeOff,
                    contentDescription = "Muted",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun PrayerTimeItem(title: String, time: String, isActive: Boolean = false) {

    val parts = time.split(" ")
    val mainTime = parts.getOrNull(0) ?: time
    val amPm = parts.getOrNull(1) ?: ""
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF202020), Color(0xFFB19768))
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(title, color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(mainTime, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(amPm, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }

}

@Composable
fun CardItem(title: String, image: Int) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0XFFE2BE7F)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
