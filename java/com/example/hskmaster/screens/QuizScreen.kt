package com.example.hskmaster.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hskmaster.data.QuestionType
import com.example.hskmaster.data.QuizData
import com.example.hskmaster.data.QuizQuestion
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    level: String, topicId: String? = null, navController: NavController
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(1500) } // 25 phút
    var isFinished by remember { mutableStateOf(false) }

    // Dành cho Sentence Ordering
    var userOrder by remember { mutableStateOf<List<String>>(emptyList()) }

    val questions = remember { QuizData.getQuestionsForLevel(level) }
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    // Timer
    LaunchedEffect(timeLeft, isFinished) {
        if (timeLeft > 0 && !isFinished) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft <= 0 && !isFinished) {

            finishQuiz(level, score, questions.size, navController, timeLeft)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$level - Kiểm tra", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}")
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
                // Header
                Text(
                    text = "Câu ${currentQuestionIndex + 1}/${questions.size} • Điểm: $score",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                LinearProgressIndicator(
                    progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
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

                // Hiển thị đáp án theo loại câu hỏi
                QuestionContent(
                    question = currentQuestion,
                    selectedAnswer = selectedAnswer,
                    userOrder = userOrder,
                    onAnswerSelected = { selectedAnswer = it },
                    onOrderChanged = { userOrder = it }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Nút tiếp theo / Nộp bài
                Button(
                    onClick = {
                        val isCorrect = checkAnswer(currentQuestion, selectedAnswer, userOrder)
                        if (isCorrect) score += 10

                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedAnswer = -1
                            userOrder = emptyList()
                        } else {
                            isFinished = true
                            finishQuiz(level, score, questions.size, navController, timeLeft)                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = selectedAnswer != -1 || currentQuestion.type is QuestionType.SentenceOrdering
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

// ==================== KIỂM TRA ĐÁP ÁN (ĐÃ SỬA) ====================
private fun checkAnswer(
    question: QuizQuestion,
    selected: Int,
    userOrder: List<String>
): Boolean {
    return when (val type = question.type) {
        is QuestionType.MultipleChoice -> selected == type.correctIndex
        is QuestionType.TrueFalse -> {
            val userAnswerIsTrue = (selected == 1)  // 1 = Đúng, 0 = Sai
            userAnswerIsTrue == type.correctIsTrue
        }
        is QuestionType.SentenceOrdering -> userOrder == type.correctOrder
    }
}

// ==================== HIỂN THỊ ĐÁP ÁN ====================
@Composable
private fun QuestionContent(
    question: QuizQuestion,
    selectedAnswer: Int,
    userOrder: List<String>,
    onAnswerSelected: (Int) -> Unit,
    onOrderChanged: (List<String>) -> Unit
) {
    when (val type = question.type) {
        is QuestionType.MultipleChoice -> {
            type.options.forEachIndexed { index, option ->
                AnswerOption(
                    text = option,
                    isSelected = selectedAnswer == index,
                    onClick = { onAnswerSelected(index) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        is QuestionType.TrueFalse -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                AnswerOption(
                    text = "Đúng",
                    isSelected = selectedAnswer == 1,
                    onClick = { onAnswerSelected(1) },
                    modifier = Modifier.weight(1f)
                )
                AnswerOption(
                    text = "Sai",
                    isSelected = selectedAnswer == 0,
                    onClick = { onAnswerSelected(0) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        is QuestionType.SentenceOrdering -> {
            Column {
                if (userOrder.isNotEmpty()) {
                    Text("Câu bạn sắp xếp:", fontWeight = FontWeight.Bold)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Text(
                            text = userOrder.joinToString(" "),
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text("Chọn từ theo thứ tự:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(type.words) { word ->
                        val isUsed = userOrder.contains(word)
                        ChipWord(
                            word = word,
                            isUsed = isUsed,
                            onClick = {
                                if (!isUsed) onOrderChanged(userOrder + word)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { onOrderChanged(emptyList()) }) {
                    Text("Làm lại")
                }
            }
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(18.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
private fun ChipWord(
    word: String,
    isUsed: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isUsed) Color.LightGray else MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.clickable(enabled = !isUsed) { onClick() }
    ) {
        Text(
            text = word,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            fontSize = 15.sp
        )
    }
}

// ==================== KẾT THÚC BÀI KIỂM TRA ====================
// Trong QuizScreen.kt
private fun finishQuiz(
    level: String,
    score: Int,
    total: Int,
    navController: NavController,
    timeLeft: Int          // Thêm tham số này
) {
    val timeSpentSeconds = 1500 - timeLeft          // 25 phút - thời gian còn lại
    val minutes = timeSpentSeconds / 60
    val seconds = timeSpentSeconds % 60
    val timeSpent = String.format("%02d:%02d", minutes, seconds)

    navController.navigate("hsk_result/$score/$total/$level/$timeSpent") {
        popUpTo("quiz_screen") { inclusive = true }
    }
}