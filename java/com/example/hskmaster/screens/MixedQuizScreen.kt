package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.hskmaster.data.model.*
import com.example.hskmaster.data.repository.generateMixedQuiz

@Composable
fun MixedQuizScreen(level: Int = 1) {

    var questions by remember { mutableStateOf(generateMixedQuiz()) }
    var index by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }

    var selected by remember { mutableStateOf<String?>(null) }
    var input by remember { mutableStateOf("") }
    var order by remember { mutableStateOf(listOf<String>()) }

    var showAnswer by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    if (showResult) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🎯 Điểm của bạn", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(12.dp))
            Text("$score / ${questions.size * 10}", style = MaterialTheme.typography.headlineLarge)
        }
        return
    }

    val q = questions[index]

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Câu ${index + 1}/${questions.size}")

        Spacer(modifier = Modifier.height(16.dp))

        Text(q.question, style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // =========================
        // 🧠 TRẮC NGHIỆM
        // =========================
        if (q.type == QuestionType.MULTIPLE_CHOICE) {

            q.options.forEach { option ->

                val isCorrect = option == q.correctAnswer
                val isSelected = option == selected

                val color = when {
                    showAnswer && isCorrect -> Color.Green
                    showAnswer && isSelected && !isCorrect -> Color.Red
                    else -> Color.LightGray
                }

                Button(
                    onClick = {
                        if (!showAnswer) selected = option
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Text(option)
                }
            }
        }

        // =========================
        // ✍️ ĐIỀN TỪ
        // =========================
        if (q.type == QuestionType.FILL) {

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Nhập đáp án") }
            )

            if (showAnswer) {
                if (input == q.correctAnswer) {
                    Text("✅ Đúng", color = Color.Green)
                } else {
                    Text("❌ Sai: ${q.correctAnswer}", color = Color.Red)
                }
            }
        }

        // =========================
        // 🔀 SẮP XẾP
        // =========================
        if (q.type == QuestionType.ORDER) {

            if (order.isEmpty()) {
                order = q.words
            }

            order.forEachIndexed { i, word ->
                Button(
                    onClick = {
                        if (!showAnswer && i < order.size - 1) {
                            val newList = order.toMutableList()
                            val temp = newList[i]
                            newList[i] = newList[i + 1]
                            newList[i + 1] = temp
                            order = newList
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(word)
                }
            }

            if (showAnswer) {
                if (order == q.correctOrder) {
                    Text("✅ Đúng", color = Color.Green)
                } else {
                    Text("❌ Sai: ${q.correctOrder.joinToString("")}", color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =========================
        // NÚT CHECK / NEXT
        // =========================
        Button(
            onClick = {

                if (!showAnswer) {

                    // ✅ CHẤM ĐIỂM
                    when (q.type) {

                        QuestionType.MULTIPLE_CHOICE -> {
                            if (selected == q.correctAnswer) score += 10
                        }

                        QuestionType.FILL -> {
                            if (input == q.correctAnswer) score += 10
                        }

                        QuestionType.ORDER -> {
                            if (order == q.correctOrder) score += 10
                        }
                    }

                    showAnswer = true

                } else {

                    // 👉 NEXT
                    if (index < questions.size - 1) {
                        index++
                        selected = null
                        input = ""
                        order = listOf()
                        showAnswer = false
                    } else {
                        showResult = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (!showAnswer) "Kiểm tra" else "Câu tiếp")
        }
    }
}