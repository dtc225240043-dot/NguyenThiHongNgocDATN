package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdviceScreen(onStart: () -> Unit) {

    Column(modifier = Modifier.padding(24.dp)) {

        Text("📊 Phân tích của bạn")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bạn đang ở HSK2")
        Text("Mục tiêu: HSK4")
        Text("Học 30 phút/ngày")

        Spacer(modifier = Modifier.height(16.dp))

        Text("👉 Dự kiến đạt mục tiêu sau 3 tháng")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onStart() }) {
            Text("Bắt đầu học")
        }
    }
}