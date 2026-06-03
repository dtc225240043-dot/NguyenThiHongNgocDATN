package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hskmaster.data.JsonLoader
import com.example.hskmaster.data.model.Vocabulary
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.entity.FavoriteEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun VocabularyLevelScreen(
    level: Int,
    topicId: String,

    navController: NavController
) {
    val context = LocalContext.current
    var words by remember { mutableStateOf<List<Vocabulary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(level) {
        val lessons = JsonLoader.loadLessons(context, "hsk$level.json")
        words = lessons.flatMap { it.vocabularies }
        isLoading = false
    }

    var favoriteIds by remember { mutableStateOf(setOf<String>()) }
    val db = AppDatabase.getDatabase(context)
    val favoriteDao = db.favoriteDao()

    LaunchedEffect(Unit) {
        val favs = favoriteDao.getAllFavorites()
        favoriteIds = favs.map { it.id }.toSet()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HSK $level - Từ vựng") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(words) { word ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = word.word ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = word.pinyin ?: "",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 16.sp
                                )
                                Text(text = word.meaning ?: "")
                            }

                            val id = word.id?.toString() ?: (word.word ?: "")
                            val isFav = favoriteIds.contains(id)
                            IconButton(onClick = {
                                GlobalScope.launch {
                                    if (isFav) {
                                        favoriteDao.deleteFavoriteById(id)
                                        favoriteIds = favoriteIds - id
                                    } else {
                                        val fav = FavoriteEntity(
                                            id = id,
                                            hanzi = word.hanzi,
                                            topicId = topicId,
                                            word = word.word,
                                            pinyin = word.pinyin ?: "",
                                            meaning = word.meaning ?: "",
                                            level = level,
                                            category = word.category
                                        )
                                        favoriteDao.insertFavorite(fav)
                                        favoriteIds = favoriteIds + id
                                    }
                                }
                            }) {
                                if (isFav) Icon(Icons.Default.Star, contentDescription = "Yêu thích")
                                else Icon(Icons.Default.StarBorder, contentDescription = "Thêm yêu thích")
                            }
                        }
                    }
                }

                if (words.isEmpty()) {
                    item {
                        Text(
                            text = "Không có dữ liệu từ vựng cho HSK $level",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}