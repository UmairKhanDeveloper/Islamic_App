package com.example.islamicapp.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.islamicapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(navController: NavController) {
    val pages = listOf(
        Triple(
            R.drawable.group,
            "Choose Language",
            "English / Arabic"
        ),
        Triple(
            R.drawable.kaba,
            "Welcome To Islami",
            "We Are Very Excited To Have You\nIn Our Community"
        ),
        Triple(
            R.drawable.quran,
            "Reading the Quran",
            "Read, and your Lord is the Most\nGenerous"
        ),
        Triple(
            R.drawable.bearish,
            "Bearish",
            "Praise the name of your Lord, the\nMost High"
        ),
        Triple(
            R.drawable.radio,
            "Holy Quran Radio",
            "You can listen to the Holy Quran\nRadio through the app easily"
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()
    val goldColor = Color(0xFFF9C784)
    val backgroundColor = Color(0xFF202020)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.masjid),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(210.dp)

            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val (image, title, subtitle) = pages[page]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(15.dp))

                    Image(
                        painter = painterResource(id = image),
                        contentDescription = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = title,
                        color = goldColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = subtitle,
                        color = goldColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        if (pagerState.currentPage > 0) {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                ) {
                    Text("Back", color = goldColor)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    repeat(pages.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(if (index == pagerState.currentPage) goldColor else Color.Gray)
                        )
                    }
                }

                TextButton(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.lastIndex) {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            } else {
                                navController.navigate(Screens.HomeScreen.route) {
                                    popUpTo("intro") { inclusive = true }
                                }
                            }
                        }
                    }
                ) {
                    Text(
                        if (pagerState.currentPage == pages.lastIndex) "Finish" else "Next",
                        color = goldColor
                    )
                }
            }
        }
    }
}
