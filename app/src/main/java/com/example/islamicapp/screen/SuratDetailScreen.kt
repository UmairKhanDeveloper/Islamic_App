package com.example.islamicapp.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SuratDetailScreen(
    navController: NavController,
    verses: List<String>,
    id: Int?,
    type: String,
    description: String,
    translation: String,
    surahName: String,
    surahNameAr: String,
    totalVerses: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFE2BE7F),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = surahName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFFE2BE7F)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.cornerr1),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                colorFilter = ColorFilter.tint(Color(0xFFE2BE7F))
            )

            Text(
                text = surahNameAr,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFFE2BE7F),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.cornerr2),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                colorFilter = ColorFilter.tint(Color(0xFFE2BE7F))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            itemsIndexed(verses) { index, verse ->
                if (verse.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE2BE7F),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(Color.Transparent, shape = RoundedCornerShape(14.dp))
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "[${index + 1}] $verse",
                            fontSize = 18.sp,
                            color = Color(0xFFE2BE7F),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.mosque),
            contentDescription = "mosque",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
    }
}
