package com.example.islamicapp.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class LanguageItem(
    val code: String,
    val name: String,
    val flag: String
)

@Composable
fun LanguageScreen(navController: NavController) {
    val context = LocalContext.current
    val sampleLanguages = remember {
        listOf(
            LanguageItem("en", "English", "🇬🇧"),
            LanguageItem("es", "Spanish", "🇪🇸"),
            LanguageItem("fr", "French", "🇫🇷"),
            LanguageItem("de", "German", "🇩🇪"),
            LanguageItem("ar", "Arabic", "🇸🇦"),
            LanguageItem("hi", "Hindi", "🇮🇳"),
            LanguageItem("zh", "Chinese", "🇨🇳"),
            LanguageItem("ja", "Japanese", "🇯🇵"),
            LanguageItem("ko", "Korean", "🇰🇷"),
            LanguageItem("ru", "Russian", "🇷🇺"),
            LanguageItem("it", "Italian", "🇮🇹"),
            LanguageItem("pt", "Portuguese", "🇵🇹"),
            LanguageItem("tr", "Turkish", "🇹🇷"),
            LanguageItem("ur", "Urdu", "🇵🇰"),
        )
    }
    val languageManager = remember { PreferenceManager(context) }
    val scope = rememberCoroutineScope()
    var selectedLanguageCode by remember { mutableStateOf<String?>(null) }
    var selectedLanguageName by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Your Language",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleLanguages) { language ->
                LanguageItemCard(
                    language = language,
                    isSelected = selectedLanguageCode == language.code,
                    onClick = {
                        selectedLanguageCode = language.code
                        selectedLanguageName = language.name
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedLanguageName?.let { name ->
                    scope.launch {
                        languageManager.saveLanguageName(name)
                        navController.navigate(Screens.OnBoardingScreen.route)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = selectedLanguageCode != null
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun LanguageItemCard(
    language: LanguageItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    else MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = language.flag,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = language.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
