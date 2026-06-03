package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hskmaster.data.JsonLoader
import com.example.hskmaster.data.model.Vocabulary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.ui.Alignment
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.entity.FavoriteEntity

@Composable
fun VocabScreen(

    level: Int,

    topicId: String,
) {

    val context = LocalContext.current

    var vocabList by remember {

        mutableStateOf<List<Vocabulary>>(
            emptyList()
        )
    }

    LaunchedEffect(Unit) {

        val lessons = JsonLoader.loadLessons(
            context,
            "hsk$level.json"
        )

        val lesson = lessons.find {

            it.id == topicId
        }

        vocabList =
            lesson?.vocabularies
                ?: emptyList()
    }

    var favoriteIds by remember { mutableStateOf(setOf<String>()) }

    val db = AppDatabase.getDatabase(context)
    val favoriteDao = db.favoriteDao()

    LaunchedEffect(Unit) {
        // load current favorites
        val favs = favoriteDao.getAllFavorites()
        favoriteIds = favs.map { it.id }.toSet()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "📚 Danh sách từ vựng",

                style = MaterialTheme
                    .typography
                    .headlineMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        items(vocabList) { vocab ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

                    Column(modifier = Modifier.weight(1f)) {
                        // HIỂN THỊ HANZI HOẶC WORD
                        Text(
                            text = vocab.hanzi ?: vocab.word ?: "",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(text = vocab.pinyin)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = vocab.meaning)
                    }

                    // Favorite icon
                    val id = vocab.id?.toString() ?: (vocab.hanzi ?: vocab.word ?: "")
                    val isFav = favoriteIds.contains(id)
                    IconButton(onClick = {
                        // toggle favorite - perform DB op in background
                        GlobalScope.launch {
                            if (isFav) {
                                favoriteDao.deleteFavoriteById(id)
                                favoriteIds = favoriteIds - id
                            } else {
                                val fav = FavoriteEntity(
                                    id = id,
                                    hanzi = vocab.hanzi,
                                    topicId = topicId,
                                    word = vocab.word,
                                    pinyin = vocab.pinyin ?: "",
                                    meaning = vocab.meaning ?: "",
                                    level = level,
                                    category = vocab.category
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
    }
}

