package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hskmaster.data.local.DatabaseProvider
import com.example.hskmaster.data.local.entity.TopicEntity

@Composable
fun TopicListScreen(
    level: Int,
    navController: NavController
) {

    val context = LocalContext.current

    var topics by remember {
        mutableStateOf<List<TopicEntity>>(emptyList())
    }

    LaunchedEffect(Unit) {

        val db = DatabaseProvider.getDatabase(context)
        val dao = db.topicDao()

        // Insert fake data lần đầu
        if (dao.getTopicsByLevel(level).isEmpty()) {

            val fakeTopics = listOf(

                TopicEntity(
                    id = "1",
                    title = "Bài 1: Xin chào",
                    videoUrl = "https://res.cloudinary.com/dibj4cvyl/video/upload/q_auto/f_auto/v1778256474/7805762234811_zryhsg.mp4",
                    level = level
                ),

                TopicEntity(
                    id = "2",
                    title = "Bài 2: Gia đình",
                    videoUrl = "https://res.cloudinary.com/dibj4cvyl/video/upload/q_auto/f_auto/v1778256474/7805762234811_zryhsg.mp4",
                    level = level
                )
            )

            dao.insertAll(fakeTopics)
        }

        // Load data
        topics = dao.getTopicsByLevel(level)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "📚 HSK $level",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {

            items(topics) { topic ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),

                    onClick = {

                        // ✅ FIX NAVIGATION
                        navController.navigate(
                            "topicDetail/$level/${topic.id}"
                        )
                    }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = topic.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Click để học bài này"
                        )
                    }
                }
            }
        }
    }
}