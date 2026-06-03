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
import com.example.hskmaster.data.JsonLoader
import com.example.hskmaster.data.model.LessonModel

@Composable
fun HomeTab(
    navController: NavController
) {

    val context = LocalContext.current

    val hsk1 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk1.json"
        )
    }

    val hsk2 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk2.json"
        )
    }

    val hsk3 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk3.json"
        )
    }

    val hsk4 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk4.json"
        )
    }

    val hsk5 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk5.json"
        )
    }

    val hsk6 = remember {
        JsonLoader.loadLessons(
            context,
            "hsk6.json"
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            Text(
                text = "📘 HSK 1",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk1) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 1,
                navController = navController
            )
        }

        item {

            Text(
                text = "📗 HSK 2",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk2) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 2,
                navController = navController
            )
        }

        item {

            Text(
                text = "📙 HSK 3",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk3) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 3,
                navController = navController
            )
        }

        item {

            Text(
                text = "📕 HSK 4",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk4) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 4,
                navController = navController
            )
        }

        item {

            Text(
                text = "📓 HSK 5",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk5) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 5,
                navController = navController
            )
        }

        item {

            Text(
                text = "📔 HSK 6",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(hsk6) { lesson ->

            LessonCard(
                lesson = lesson,
                level = 6,
                navController = navController
            )
        }
    }
}

@Composable
fun LessonCard(
    lesson: LessonModel,
    level: Int,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),

        onClick = {

            // ✅ FIX CRASH
            navController.navigate(
                "topicDetail/$level/${lesson.id}"
            )
        },

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Click để học bài này",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}