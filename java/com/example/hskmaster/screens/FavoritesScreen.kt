package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hskmaster.data.local.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val favoriteDao = db.favoriteDao()

    var favorites by remember { mutableStateOf(listOf(com.example.hskmaster.data.local.entity.FavoriteEntity("","", "", "", "", "", 0, null))) }

    LaunchedEffect(Unit) {
        favorites = favoriteDao.getAllFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Từ vựng yêu thích") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (favorites.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Bạn chưa có từ vựng yêu thích nào.")
                    }
                }
            } else {
                items(favorites) { fav ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = fav.hanzi ?: fav.word ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = fav.pinyin)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = fav.meaning)
                            }
                            IconButton(onClick = {
                                GlobalScope.launch {
                                    favoriteDao.deleteFavoriteById(fav.id)
                                    val list = favoriteDao.getAllFavorites()
                                    favorites = list
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Xóa khỏi yêu thích")
                            }
                        }
                    }
                }
            }
        }
    }
}

