package com.example.islamicapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Surah(
    val nameEnglish: String,
    val nameArabic: String,
    val verseCount: Int
)

val recentSurahs = listOf(
    Surah("Al-Anbiya", "الأنبياء", 112),
    Surah("Al-Baqarah", "البقرة", 286),
    Surah("Yaseen", "يس", 83)
)

val allSurahs = listOf(
    Surah("Al-Fatiha", "الفاتحة", 7),
    Surah("Al-Baqarah", "البقرة", 286),
    Surah("Aal-E-Imran", "آل عمران", 200),
    Surah("An-Nisa", "النساء", 176),
    Surah("Al-Maidah", "المائدة", 120)
)


@Composable
fun QuranScreen(navController: NavController) {
    var textField by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.masjid),
                contentDescription = null,
                modifier = Modifier.size(210.dp),
                contentScale = ContentScale.Crop
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CustomStyledTextField(
                    value = textField,
                    onValueChange = { textField = it }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Most Recently",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recentSurahs.size) { index ->
                    CardItemMostRecently(surah = recentSurahs[index])
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Suras List",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )


            Column(modifier = Modifier.fillMaxWidth()) {
                allSurahs.forEachIndexed { index, surah ->
                    SuratCartItem(surah = surah, index = index)
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomStyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFDAC089), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = com.example.islamicapp.R.drawable.quran1),
                contentDescription = "Quran Icon",
                tint = Color(0xFFDAC089),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = "Sura Name",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    cursorBrush = SolidColor(Color(0xFFE2BE7F))
                )
            }
        }
    }
}


@Composable
fun CardItemMostRecently(surah: Surah) {
    Card(
        modifier = Modifier
            .size(width = 281.dp, height = 100.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE2BE7F)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = surah.nameEnglish,
                    color = Color(0xFF202020),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = surah.nameArabic,
                    color = Color(0xFF202020),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${surah.verseCount} Verses",
                    color = Color(0xFF202020),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.rectengle),
                contentDescription = "Sura Image",
                modifier = Modifier
                    .size(width = 120.dp, height = 130.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
@Composable
fun SuratCartItem(surah: Surah, index: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = com.example.islamicapp.R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${index + 1}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = surah.nameEnglish,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${surah.verseCount} Verses",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Text(
                text = surah.nameArabic,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        )
    }
}
