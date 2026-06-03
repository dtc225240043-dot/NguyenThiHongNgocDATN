package com.example.hskmaster.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Timer

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

data class HskTestItem(
    val level: String,
    val title: String,
    val description: String,
    val numberOfQuestions: Int,
    val timeMinutes: Int,
    val color: Color
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HskTestListScreen(
    navController: NavController
) {
    val testList = listOf(
        HskTestItem(
            level = "HSK 1",
            title = "Cơ bản - Nền tảng",
            description = "Phù hợp cho người mới bắt đầu",
            numberOfQuestions = 15,
            timeMinutes = 15,
            color = Color(0xFF2FA84F)
        ),
        HskTestItem(
            level = "HSK 2",
            title = "Sơ cấp",
            description = "Xây dựng vốn từ vựng hàng ngày",
            numberOfQuestions = 15,
            timeMinutes = 20,
            color = Color(0xFF2478D4)
        ),
        HskTestItem(
            level = "HSK 3",
            title = "Trung cấp",
            description = "Giao tiếp cơ bản trong đời sống",
            numberOfQuestions = 15,
            timeMinutes = 25,
            color = Color(0xFFD87900)
        ),
        HskTestItem(
            level = "HSK 4",
            title = "Nâng cao",
            description = "Đạt trình độ sử dụng tốt",
            numberOfQuestions = 15,
            timeMinutes = 30,
            color = Color(0xFF6B45B8)
        ),
        HskTestItem(
            level = "HSK 5",
            title = "Cao cấp",
            description = "Sử dụng tiếng Trung linh hoạt",
            numberOfQuestions = 15,
            timeMinutes = 35,
            color = Color(0xFF159C91)
        ),
        HskTestItem(
            level = "HSK 6",
            title = "Thượng cấp",
            description = "Trình độ gần như bản ngữ",
            numberOfQuestions = 15,
            timeMinutes = 40,
            color = Color(0xFFD93B63)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test theo HSK", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(testList) { test ->
                HskTestCard(
                    test = test,
                    onClick = {
                        navController.navigate("quiz_screen/${test.level}")

                    }
                )
            }
        }
    }
}

@Composable
private fun HskTestCard(
    test: HskTestItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Level Circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(test.color.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = test.level,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = test.color
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = test.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = test.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${test.numberOfQuestions} câu • ${test.timeMinutes} phút",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = test.color
            )
        }
    }
}