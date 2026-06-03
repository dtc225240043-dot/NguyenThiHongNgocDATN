package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.hskmaster.data.model.Grammar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarScreen(
    hskLevel: Int,
    topicId: String,
    navController: NavController
) {

    val context = LocalContext.current

    var grammarList by remember { mutableStateOf<List<Grammar>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(hskLevel, topicId) {
        val lessons = JsonLoader.loadLessons(context, "hsk$hskLevel.json")
        val lesson = lessons.find { it.id == topicId }
        grammarList = lesson?.grammars ?: emptyList()
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ngữ pháp HSK $hskLevel") },
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
                    .padding(16.dp)
            ) {
                items(grammarList) { grammar ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = grammar.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = grammar.content)
                        }
                    }
                }

                if (grammarList.isEmpty()) {
                    item {
                        Text("Không có dữ liệu ngữ pháp cho bài này", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}