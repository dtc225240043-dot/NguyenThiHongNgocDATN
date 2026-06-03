package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hskmaster.data.model.UserProgress
import com.example.hskmaster.data.repository.UserRepository

@Composable
fun OnboardingScreen(onDone: () -> Unit) {

    val repo = UserRepository()

    var level by remember { mutableStateOf(1) }
    var goal by remember { mutableStateOf(4) }
    var time by remember { mutableStateOf(30) }

    Column(modifier = Modifier.padding(24.dp)) {

        Text("Bạn đang ở HSK mấy?")
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { level = 1 }) { Text("HSK1") }
        Button(onClick = { level = 2 }) { Text("HSK2") }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Mục tiêu của bạn?")
        Button(onClick = { goal = 4 }) { Text("HSK4") }
        Button(onClick = { goal = 5 }) { Text("HSK5") }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bạn học bao nhiêu phút/ngày?")
        Button(onClick = { time = 30 }) { Text("30 phút") }
        Button(onClick = { time = 60 }) { Text("60 phút") }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val data = UserProgress(level, goal, time)
            repo.saveUserProgress(data)

            onDone()
        }) {
            Text("Hoàn thành")
        }
    }
}