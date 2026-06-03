package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    onRetry: () -> Unit = {},
    onBack: () -> Unit = {}
) {

    val maxScore = total * 10

    val resultText = when {
        score >= maxScore * 0.9 -> "🔥 Xuất sắc"
        score >= maxScore * 0.7 -> "👍 Tốt"
        score >= maxScore * 0.5 -> "🙂 Trung bình"
        else -> "💪 Cần cố gắng"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🎉 Kết quả",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Điểm của bạn:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$score / $maxScore",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = resultText,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 🔥 BUTTON
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(onClick = onRetry) {
                Text("🔄 Làm lại")
            }

            Button(onClick = onBack) {
                Text("🏠 Trang chủ")
            }
        }
    }
}