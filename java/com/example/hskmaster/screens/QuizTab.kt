package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api   // ← Thêm dòng này

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun QuizTab(
    navController: NavController,
    onCategoryClick: (String) -> Unit = {}
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Kiểm tra / Thi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },

                actions = {

                    IconButton(
                        onClick = {

                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Lịch sử"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFAF8FF)
                )
            )
        },

        containerColor = Color(0xFFFAF8FF)

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {

            Text(
                text = "Chọn bài kiểm tra phù hợp với bạn",
                fontSize = 16.sp,
                color = Color(0xFF8B8798)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // 📊 TEST HSK
            // =========================

            TestTypeCard(
                icon = "📊",
                title = "Test theo HSK",
                description = "Kiểm tra theo từng cấp độ HSK",
                subtitle = "HSK 1 → HSK 6",
                color = Color(0xFF6B45B8),

                onClick = {
                    navController.navigate("hsk_test_list")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // 📋 TEST TỔNG HỢP
            // =========================

            TestTypeCard(
                icon = "📋",
                title = "Test tổng hợp",
                description = "Đánh giá tổng quát trình độ",
                subtitle = "Nghe • Đọc • Từ vựng • Ngữ pháp",
                color = Color(0xFF12B8A6),

                onClick = {
                    navController.navigate("placement_test")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "🔥 Hệ thống chấm điểm",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("• Trả lời đúng: +10 điểm")
                    Text("• Hoàn thành bài test: +XP")
                    Text("• Tăng level theo XP")
                    Text("• Streak học mỗi ngày")
                    Text("• Lưu lịch sử bài thi")
                }
            }
        }
    }
}

@Composable
private fun TestTypeCard(
    icon: String,
    title: String,
    description: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Row(

            modifier = Modifier.padding(20.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color.copy(alpha = 0.12f),
                        RoundedCornerShape(16.dp)
                    ),

                contentAlignment = Alignment.Center

            ) {

                Text(
                    text = icon,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 15.sp,
                    color = Color(0xFF8B8798)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = color
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = color
            )
        }
    }
}

@Composable
private fun QuestionTypeChip(
    text: String
) {

    Surface(

        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),

        color = Color(0xFFF5F3FF)

    ) {

        Text(

            text = text,

            modifier = Modifier
                .padding(vertical = 14.dp)
                .fillMaxWidth(),

            textAlign = TextAlign.Center,

            fontSize = 14.sp,

            fontWeight = FontWeight.Medium
        )
    }
}