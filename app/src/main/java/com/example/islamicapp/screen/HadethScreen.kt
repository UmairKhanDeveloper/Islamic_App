package com.example.islamicapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.islamicapp.api.MainViewModel
import com.example.islamicapp.api.Repository
import com.example.islamicapp.api.ResultState
import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.db.MostRecentlyDataBase
import io.ktor.client.content.LocalFileContent
import java.time.format.TextStyle


@Composable
fun HadethScreen(navController: NavController) {
    var textField by remember { mutableStateOf("") }
    val context = LocalContext.current
    val mostRecentlyDataBase = remember { MostRecentlyDataBase.getDataBase(context) }
    val repository = remember { Repository(mostRecentlyDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val state by viewModel.allHadiths.collectAsState()
    var allHadith by remember { mutableStateOf<Hadiths?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadAllHadiths()
    }

    when (val s = state) {
        is ResultState.Error -> {
            isLoading = false
            errorText = s.error?.message ?: s.error.toString()
        }
        ResultState.Loading -> {
            isLoading = true
            errorText = null
        }
        is ResultState.Succses<*> -> {
            isLoading = false
            errorText = null
            allHadith = s.response as? Hadiths
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
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.masjid),
                contentDescription = null,
                modifier = Modifier.size(210.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CustomStyledTextFieldHadeth(
                    value = textField,
                    onValueChange = { textField = it }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            when {
                isLoading -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(3) {
                            ShimmerHadithCard()
                        }
                    }
                }
                errorText != null -> {
                    Text(text = errorText ?: "", color = Color.White)
                }
                else -> {
                    val all = allHadith?.hadiths?.data ?: emptyList()
                    val query = textField.trim()
                    val filteredList = if (query.isEmpty()) {
                        all
                    } else {
                        all.filter { h ->
                            (h.hadithEnglish ?: "").contains(query, ignoreCase = true) ||
                                    (h.englishNarrator ?: "").contains(query, ignoreCase = true) ||
                                    (h.hadithUrdu ?: "").contains(query, ignoreCase = true) ||
                                    (h.hadithArabic ?: "").contains(query, ignoreCase = true)
                        }
                    }

                    if (filteredList.isEmpty()) {
                        Text("Hadith is not found", color = Color.White)
                    } else {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(1),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(filteredList) { hadith ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    val title = "حدیث #${hadith.hadithNumber ?: "-"}"
                                    val book = hadith.bookSlug ?: "نامعلوم"
                                    val writer = hadith.book?.writerName ?: "نامعلوم"

                                    val content = hadith.hadithUrdu
                                        ?: hadith.hadithEnglish
                                        ?: hadith.hadithArabic
                                        ?: "متن دستیاب نہیں"

                                    HadithCard(
                                        title = title,
                                        content = content,
                                        book = book,
                                        writer = writer
                                    )
                                }
                            }
                        }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HadithCard(
    title: String,
    content: String,
    book: String,
    writer: String
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE2BE7F)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = com.example.islamicapp.R.drawable.cornerr1),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(id = com.example.islamicapp.R.drawable.cornerr2),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = com.example.islamicapp.R.drawable.hadithcardbackground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = " $book",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF444444)
                            )
                        )

                        Text(
                            text = "$writer",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF444444)
                            )
                        )



                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = content,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = com.example.islamicapp.R.drawable.mosque),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
@Composable
fun ShimmerHadithCard() {
    val brush = shimmerBrush()

    Card(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(180.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
