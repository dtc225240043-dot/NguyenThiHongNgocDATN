package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun VocabularyCategoryScreen(
    category: String,
    navController: NavController
) {
    val context = LocalContext.current
    var words by remember { mutableStateOf<List<Vocabulary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchText by remember { mutableStateOf("") }

    // Load dữ liệu theo category
    LaunchedEffect(category) {
        try {
            val fileName = when (category) {
                "Du lịch" -> "travel.json"
                "Nhà hàng" -> "restaurant.json"
                "Công nghệ" -> "technology.json"
                "Kinh doanh" -> "business.json"
                "Trường học" -> "school.json"
                else -> "hsk1.json"
            }
            words = JsonLoader.loadVocabulary(context, fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            words = emptyList()
        }
        isLoading = false
    }

    // Load favorites
    var favoriteIds by remember { mutableStateOf(setOf<String>()) }
    val db = AppDatabase.getDatabase(context)
    val favoriteDao = db.favoriteDao()

    LaunchedEffect(Unit) {
        val favs = favoriteDao.getAllFavorites()
        favoriteIds = favs.map { it.id }.toSet()
    }

    val filteredWords = words.filter { vocab ->
        (vocab.word ?: "").contains(searchText, ignoreCase = true) ||
                (vocab.pinyin ?: "").contains(searchText, ignoreCase = true) ||
                (vocab.meaning ?: "").contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = category,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Thanh tìm kiếm
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tìm từ vựng...") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredWords.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Không tìm thấy từ vựng nào", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn {
                    items(filteredWords) { vocab ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = vocab.word ?: "",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = vocab.pinyin ?: "",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = vocab.meaning ?: "",
                                        fontSize = 15.sp
                                    )
                                }

                                val id = vocab.id?.toString() ?: (vocab.word ?: "")
                                val isFav = favoriteIds.contains(id)
                                IconButton(onClick = {
                                    GlobalScope.launch {
                                        if (isFav) {
                                            favoriteDao.deleteFavoriteById(id)
                                            favoriteIds = favoriteIds - id
                                        } else {
                                            val fav = FavoriteEntity(
                                                id = id,
                                                hanzi = vocab.hanzi,
                                                topicId = "",
                                                word = vocab.word,
                                                pinyin = vocab.pinyin ?: "",
                                                meaning = vocab.meaning ?: "",
                                                level = 0,
                                                category = vocab.category ?: category
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
        }
    }
}