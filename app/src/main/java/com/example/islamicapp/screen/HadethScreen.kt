package com.example.islamicapp.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.islamicapp.api.MainViewModel
import com.example.islamicapp.api.Repository
import com.example.islamicapp.api.ResultState
import com.example.islamicapp.apiclient.Data
import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.db.MostRecentlyDataBase


@Composable
fun HadethScreen(navController: NavController) {
    var textField by remember { mutableStateOf("") }
    val context = LocalContext.current
    val mostRecentlyDataBase = remember { MostRecentlyDataBase.getDataBase(context) }
    val repository = remember { Repository(mostRecentlyDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val state by viewModel.allHadiths.collectAsState()
    var allHadith by remember { mutableStateOf<Hadiths?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadAllHadiths()
    }

    val isLoading = state is ResultState.Loading
    val errorText = (state as? ResultState.Error)?.error?.message

    val filteredList: List<Data> = remember(textField, allHadith) {
        allHadith?.hadiths?.data?.filter { hadith ->
            textField.isBlank() ||
                    hadith.hadithEnglish?.contains(textField, ignoreCase = true) == true ||
                    hadith.englishNarrator?.contains(textField, ignoreCase = true) == true
        } ?: emptyList()
    }

    LaunchedEffect(state) {
        if (state is ResultState.Succses<*>) {
            allHadith = (state as ResultState.Succses<Hadiths>).response
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = com.example.islamicapp.R.drawable.background3),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.masjid),
                contentDescription = null,
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomStyledTextFieldHadeth(
                value = textField,
                onValueChange = { textField = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorText?.let { Text(it, color = Color.Red) }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isLoading) {
                    items(6) {
                        ShimmerHadithCard()
                    }
                } else {
                    items(filteredList) { hadith ->
                        HadithCard(hadith = hadith,navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomStyledTextFieldHadeth(
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
                        text = "Hadith Name",
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
fun HadithCard(hadith: Data,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    HadithDetailNavArg(
                        hadith.id,
                        hadith.hadithNumber,
                        hadith.englishNarrator,
                        hadith.hadithEnglish,
                        hadith.hadithUrdu,
                        hadith.urduNarrator,
                        hadith.hadithArabic,
                        hadith.headingArabic,
                        hadith.headingUrdu,
                        hadith.headingEnglish,
                    )
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE2BE7F)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF8E8C2)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = hadith.hadithNumber ?: "",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                hadith.book?.writerName?.let { writer ->
                    Text(
                        text = writer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF202020)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                hadith.englishNarrator?.let { narrator ->
                    Text(
                        text = narrator,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                hadith.hadithEnglish?.let { hadithText ->
                    Text(
                        text = hadithText,
                        fontSize = 14.sp,
                        color = Color(0xFF202020),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerHadithCard() {
    val brush = shimmerBrush1()
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(brush)
            .padding(16.dp)
    )
}

@Composable
fun shimmerBrush1(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.White.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing)
        ),
        label = "shimmer"
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value - 1000f, 0f),
        end = Offset(translateAnim.value, 0f)
    )
}
