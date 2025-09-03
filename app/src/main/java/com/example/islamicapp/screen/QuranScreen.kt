package com.example.islamicapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.islamicapp.R
import com.example.islamicapp.api.MainViewModel
import com.example.islamicapp.api.Repository
import com.example.islamicapp.api.ResultState
import com.example.islamicapp.apiclient.Quran
import com.example.islamicapp.db.MostRecently
import com.example.islamicapp.db.MostRecentlyDataBase

@Composable
fun QuranScreen(navController: NavController) {
    val context = LocalContext.current
    val mostRecentlyDataBase = remember { MostRecentlyDataBase.getDataBase(context) }
    val repository = remember { Repository(mostRecentlyDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    var textField by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val state by viewModel.allQuran.collectAsState()
    val allMostRecently by viewModel.allMostRecently.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadAllSurahs()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
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
                painter = painterResource(id = R.drawable.masjid),
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

            when (state) {
                is ResultState.Error -> {
                    val error = (state as ResultState.Error).error
                    Text(text = "Error: $error", color = Color.Red)
                }

                ResultState.Loading -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (textField.isBlank()) {
                            Text(
                                text = "Most Recently",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
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
                                items(5) {
                                    ShimmerMostRecentCard()
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        if (textField.isBlank()) {
                            Text(
                                text = "Suras List",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp)
                            )
                        }

                        Column {
                            repeat(8) { ShimmerSurahListItem() }
                        }
                    }
                }

                is ResultState.Succses -> {
                    val allQuranData = (state as ResultState.Succses<List<Quran>>).response

                    if (textField.isBlank()) {
                        Text(
                            text = "Most Recently",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
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
                            items(allMostRecently) { index ->
                                CardItemMostRecently(
                                    mostRecently = index,
                                    navController = navController,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    if (textField.isBlank()) {
                        Text(
                            text = "Suras List",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        )
                    }

                    val filteredList = if (textField.isNotBlank()) {
                        allQuranData.filter { surah ->
                            surah.surah_name.contains(textField, ignoreCase = true) ||
                                    surah.surah_name_ar.contains(textField)
                        }
                    } else {
                        allQuranData
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        filteredList.forEachIndexed { index, surah ->
                            SuratCartItem(
                                surah = surah,
                                index = index,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        if (filteredList.isEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "No Surah found",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
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
                painter = painterResource(id = R.drawable.quran1),
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
fun CardItemMostRecently(mostRecently: MostRecently, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable { navController.navigate(Screens.SuratDetailScreen.route) }
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
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = mostRecently.titleEng,
                    color = Color(0xFF202020),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mostRecently.titleArabic,
                    color = Color(0xFF202020),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =
                        "${mostRecently.total_verses} Verses",
                    color = Color(0xFF202020),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter =
                    painterResource(id = com.example.islamicapp.R.drawable.rectengle),
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
fun SuratCartItem(
    surah: Quran,
    index: Int,
    navController: NavController,
    viewModel: MainViewModel
) {
    Row(
        modifier = Modifier
            .clickable {
                val versesList = surah.verses.values.map { it.content ?: "" }
                navController.navigate(
                    Quran1(
                        description = surah.description,
                        id = surah.id,
                        surah_name = surah.surah_name,
                        surah_name_ar = surah.surah_name_ar,
                        total_verses = surah.total_verses,
                        translation = surah.translation,
                        type = surah.type,
                        verses = versesList
                    )
                )

                viewModel.insertMostRecently(
                    MostRecently(
                        id = surah.id,
                        titleEng = surah.surah_name,
                        titleArabic = surah.surah_name_ar,
                        image = "",
                        total_verses = surah.total_verses.toString()
                    )
                )
            }
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
                text = surah.surah_name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${surah.total_verses} Verses",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Text(
            text = surah.surah_name_ar,
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
@Composable
fun ShimmerMostRecentCard() {
    val brush = shimmerBrush()
    Spacer(
        modifier = Modifier
            .size(width = 281.dp, height = 100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(brush)
    )
}

@Composable
fun ShimmerSurahListItem() {
    val brush = shimmerBrush()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(brush)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.3f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
    }
}

@Composable
fun shimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.White.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1200)),
        label = ""
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = 0f)
    )
}