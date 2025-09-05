package com.example.islamicapp.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.chrono.HijrahChronology
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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
                    image = com.example.islamicapp.R.drawable.evning,
                    subtitle = "Tap to Read",
                    onClick = { navController.navigate(Screens.EveningAzkar.route) }
                )

                CardItem(
                    title = "Morning Azkar",
                    image = com.example.islamicapp.R.drawable.morinig,
                    subtitle = "Tap to Listen",
                    onClick = { navController.navigate(Screens.MorningAzkar.route) }
                )
            }


            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun rememberCurrentTime(): State<LocalDateTime> {
    val currentTime = remember { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableStateOf(LocalDateTime.now())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = LocalDateTime.now()
            delay(1000L)
        }
    }
    return currentTime
}

fun getNextPrayer(current: LocalTime, prayerTimes: Map<String, LocalTime>): Pair<String, Duration>? {
    val upcoming = prayerTimes
        .mapValues { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Duration.between(current, it.value)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        }
        .filter { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.value > Duration.ZERO
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        }
        .minByOrNull { it.value }

    return upcoming?.toPair()
}

@Composable
fun PrayerTimeUI() {
    val currentTime by rememberCurrentTime()

    val prayers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mapOf(
            "Fajr" to LocalTime.of(4, 4),
            "Dhuhr" to LocalTime.of(13, 1),
            "Asr" to LocalTime.of(16, 38),
            "Maghrib" to LocalTime.of(19, 57),
            "Isha" to LocalTime.of(21, 24)
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    val nextPrayer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getNextPrayer(currentTime.toLocalTime(), prayers)
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(280.dp)
            .clip(RoundedCornerShape(40.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE2BE7F))
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
                Column {
                    Text(
                        currentTime.format(DateTimeFormatter.ofPattern("dd MMM,")),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        currentTime.year.toString(),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Prayer Times",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        currentTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                val hijriDate = rememberHijriDate(currentTime)
                Column(horizontalAlignment = Alignment.End) {
                    Text(hijriDate.first, fontSize = 14.sp, color = Color.Black)
                    Text(hijriDate.second, fontSize = 14.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                prayers.forEach { (name, time) ->
                    val formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        time.format(DateTimeFormatter.ofPattern("hh:mm a"))
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    PrayerTimeItem(
                        title = name,
                        time = formatted,
                        isActive = nextPrayer?.first == name
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            nextPrayer?.let { (name, duration) ->
                val countdown by produceState(initialValue = duration, currentTime) {
                    while (true) {
                        val newDuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Duration.between(
                                currentTime.toLocalTime(),
                                prayers[name]
                            )
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        value = newDuration
                        delay(1000L)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Next Prayer - $name in ${formatDuration(countdown)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Icon(Icons.Default.VolumeOff, contentDescription = null, tint = Color.Black)
                }
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
                        colors = if (isActive) listOf(Color(0xFFB19768), Color(0xFF202020))
                        else listOf(Color(0xFF202020), Color(0xFFB19768))
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
fun CardItem(
    title: String,
    image: Int,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp)
            .clickable { onClick() },
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                subtitle?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun rememberHijriDate(currentTime: LocalDateTime): Pair<String, String> {
    val hijriDate = HijrahChronology.INSTANCE.date(currentTime.toLocalDate())

    val day = hijriDate.get(ChronoField.DAY_OF_MONTH).toString().padStart(2, '0')
    val monthIndex = hijriDate.get(ChronoField.MONTH_OF_YEAR) // 1-12
    val year = hijriDate.get(ChronoField.YEAR).toString()

    val hijriMonths = listOf(
        "Muh", "Saf", "Rab-I", "Rab-II", "Jum-I", "Jum-II",
        "Raj", "Sha", "Ram", "Shaw", "Dhu-Q", "Dhu-H"
    )
    val month = hijriMonths.getOrNull(monthIndex - 1) ?: ""

    return "$day $month" to year
}
fun formatDuration(duration: Duration): String {
    val hours = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        duration.toHours()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    return String.format("%02dh %02dm %02ds", hours, minutes, seconds)
}
