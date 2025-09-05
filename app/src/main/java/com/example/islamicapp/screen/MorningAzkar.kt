package com.example.islamicapp.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Azkar(
    val arabic: String,
    val translation: String,
    val reference: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorningAzkar(navController: NavController) {
    val azkarList = listOf(
        Azkar(
            arabic = "أصبَحْنا وأصبَح المُلكُ لله، والحمدُ لله، لا إلهَ إلاّ الله وحدَه لا شريكَ له، له المُلكُ وله الحمدُ وهو على كلّ شيءٍ قدير",
            translation = "We have reached the morning and so too has the dominion, all praise is due to Allah. None has the right to be worshipped except Allah, alone, without partner. To Him belongs all sovereignty and praise and He is over all things competent.",
            reference = "Abu Dawood 5068"
        ),
        Azkar(
            arabic = "اللّهـمَّ إنِّي أسْألُكَ خَيْرَ هذا اليَومِ، فَتْحَهُ، ونَصْرَهُ، ونُورَهُ، وبَرَكَتَهُ، وهُداهُ، وأعُوذُ بِكَ مِنْ شَرِّ ما فيهِ وشَرِّ ما بَعْدَه",
            translation = "O Allah, I ask You for the good of this day, its victory, its light, its blessing, and its guidance. And I seek refuge in You from the evil of this day and the evil that comes after it.",
            reference = "Abu Dawood 5090"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Morning Azkar", color = Color.White) }, navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() })

                },
                colors =
                    TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B1B1B))
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

@Composable
fun AzkarCard(azkar: Azkar) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0XFFE2BE7F)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1B1B))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = azkar.arabic,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = azkar.translation,
                fontSize = 14.sp,
                color = Color(0xFFE2BE7F)
            )

            Text(
                text = azkar.reference,
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
        }
    }
}
