package com.example.hskmaster.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveTestScreen(
    navController: NavController,
    onTestCompleted: (String) -> Unit  // Format: "điểm:trìnhđộ" ví dụ "42:HSK 4"
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(1800) } // 30 phút
    var isFinished by remember { mutableStateOf(false) }

    // Timer countdown
    LaunchedEffect(timeLeft) {
        if (timeLeft > 0 && !isFinished) {
            delay(1000)
            timeLeft--
        } else if (timeLeft <= 0) {
            finishTest(score, onTestCompleted)
        }
    }

    val questions = remember { getComprehensiveTestQuestions() }
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Tổng Hợp", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (isFinished) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (currentQuestion != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Progress
                Text(
                    text = "Câu ${currentQuestionIndex + 1}/${questions.size}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1) / questions.size.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Câu hỏi
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = currentQuestion.question,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(20.dp),
                        fontWeight = FontWeight.Medium,
                        lineHeight = 26.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Các đáp án
                currentQuestion.options.forEachIndexed { index, option ->
                    AnswerOption(
                        text = option,
                        isSelected = selectedAnswer == index,
                        onClick = { selectedAnswer = index }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (selectedAnswer != -1) {
                            if (selectedAnswer == currentQuestion.correctAnswer) score++

                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                selectedAnswer = -1
                            } else {
                                isFinished = true
                                finishTest(score, onTestCompleted)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedAnswer != -1,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (currentQuestionIndex < questions.size - 1) "Câu tiếp theo" else "Nộp bài",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(18.dp),
            fontSize = 16.sp
        )
    }
}

private fun finishTest(score: Int, onTestCompleted: (String) -> Unit) {
    val percentage = (score * 100) / 50
    val level = when {
        percentage >= 85 -> "HSK 6"
        percentage >= 75 -> "HSK 5"
        percentage >= 65 -> "HSK 4"
        percentage >= 50 -> "HSK 3"
        percentage >= 35 -> "HSK 2"
        else -> "HSK 1"
    }
    onTestCompleted("$score:$level")
}

// ==================== DỮ LIỆU CÂU HỎI (Bạn có thể mở rộng) ====================
private data class TestQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)

private fun getComprehensiveTestQuestions(): List<TestQuestion> {
    return listOf(
        TestQuestion(
            "Chọn từ đồng nghĩa với \"phát triển\":",
            listOf("tiện bị", "hiện đại", "phát triển", "cơ điện"),
            2
        ),
        TestQuestion(
            "\"我喜欢吃苹果\" dịch là gì?",
            listOf("Tôi thích uống nước", "Tôi thích ăn táo", "Tôi thích học tiếng Trung", "Tôi thích mua sách"),
            1
        ),
        TestQuestion(
            "Câu nào đúng ngữ pháp?",
            listOf(
                "Tôi đi học muộn hôm nay.",
                "Hôm nay tôi đi muộn học.",
                "Tôi hôm nay đi học muộn.",
                "Muộn hôm nay tôi đi học."
            ),
            0
        ),
        // Thêm nhiều câu hỏi nữa ở đây (tổng khuyến nghị 20-30 câu)
    )
}