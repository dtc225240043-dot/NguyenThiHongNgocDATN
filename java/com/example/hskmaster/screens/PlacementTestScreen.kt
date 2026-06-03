package com.example.hskmaster.screens

import android.os.SystemClock
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.hskmaster.logic.PlacementTestLogic
import com.example.hskmaster.logic.PlacementTestModel

@Composable
fun PlacementTestScreen(
    navController: NavController,  // Đảm bảo đã truyền NavController
    onTestCompleted: (String) -> Unit // Đảm bảo có onTestCompleted để hiển thị kết quả
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Thời gian bắt đầu kiểm tra
    var startTime by remember { mutableStateOf(SystemClock.elapsedRealtime()) }

    // Các câu trả lời ban đầu của người dùng
    var question1Answer by remember { mutableStateOf<String?>(null) }
    var question2Answer by remember { mutableStateOf<String?>(null) }
    var question3Answer by remember { mutableStateOf<String?>(null) }
    var question4Answer by remember { mutableStateOf<String?>(null) }
    var question5Answer by remember { mutableStateOf<String?>(null) }
    var question6Answer by remember { mutableStateOf<String?>(null) }
    var question7Answer by remember { mutableStateOf<String?>(null) }
    var question8Answer by remember { mutableStateOf<String?>(null) }
    var question9Answer by remember { mutableStateOf<String?>(null) }
    var question10Answer by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }
    var elapsedTime by remember { mutableStateOf(0L) }

    // Column for the test UI with scrollable content
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Nội dung bài kiểm tra có thể cuộn được
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("📋 Placement Test", style = MaterialTheme.typography.headlineMedium)

            // Câu 1
            Text("Câu 1: Chọn nghĩa đúng của từ: 你好 (nǐ hǎo)")
            RadioButtonWithOptions(
                options = listOf("A. Tạm biệt", "B. Xin chào", "C. Cảm ơn", "D. Xin lỗi"),
                selectedAnswer = question1Answer,
                onAnswerChanged = { question1Answer = it }
            )

            // Câu 2
            Text("Câu 2: Chọn phiên âm đúng của từ: 谢谢")
            RadioButtonWithOptions(
                options = listOf("A. xièxie", "B. xiexi", "C. xièxi", "D. xiexe"),
                selectedAnswer = question2Answer,
                onAnswerChanged = { question2Answer = it }
            )

            // Câu 3
            Text("Câu 3: Điền từ thích hợp: 我___学生。")
            RadioButtonWithOptions(
                options = listOf("A. 是", "B. 有", "C. 在", "D. 做"),
                selectedAnswer = question3Answer,
                onAnswerChanged = { question3Answer = it }
            )

            // Câu 4
            Text("Câu 4: Câu nào đúng ngữ pháp?")
            RadioButtonWithOptions(
                options = listOf("A. 我很喜欢吃苹果。", "B. 我喜欢很吃苹果。", "C. 我吃喜欢很苹果。", "D. 很我喜欢吃苹果。"),
                selectedAnswer = question4Answer,
                onAnswerChanged = { question4Answer = it }
            )

            // Câu 5
            Text("Câu 5: Chọn câu phủ định đúng:")
            RadioButtonWithOptions(
                options = listOf("A. 我不有钱。", "B. 我没有钱。", "C. 我没是钱。", "D. 我不是有钱。"),
                selectedAnswer = question5Answer,
                onAnswerChanged = { question5Answer = it }
            )

            // Câu 6
            Text("Câu 6: Sắp xếp câu đúng: (1) 在 (2) 我 (3) 学校 (4) 学习")
            RadioButtonWithOptions(
                options = listOf("A. 2-1-3-4", "B. 2-3-1-4", "C. 1-2-3-4", "D. 2-1-4-3"),
                selectedAnswer = question6Answer,
                onAnswerChanged = { question6Answer = it }
            )

            // Câu 7
            Text("Câu 7: Chọn từ phù hợp: 他昨天___去北京。")
            RadioButtonWithOptions(
                options = listOf("A. 会", "B. 要", "C. 已经", "D. 正在"),
                selectedAnswer = question7Answer,
                onAnswerChanged = { question7Answer = it }
            )

            // Câu 8
            Text("Câu 8: Hiểu câu: 这个问题比那个问题难。")
            RadioButtonWithOptions(
                options = listOf("A. Câu này dễ hơn câu kia", "B. Câu này khó hơn câu kia", "C. Hai câu giống nhau", "D. Không so sánh"),
                selectedAnswer = question8Answer,
                onAnswerChanged = { question8Answer = it }
            )

            // Câu 9
            Text("Câu 9: Chọn câu đúng:")
            RadioButtonWithOptions(
                options = listOf("A. 如果你来，我很高兴。", "B. 如果你来，我会很高兴。", "C. 如果你来，我很会高兴。", "D. 如果你来，我高兴很。"),
                selectedAnswer = question9Answer,
                onAnswerChanged = { question9Answer = it }
            )

            // Câu 10
            Text("Câu 10: Chọn câu dùng đúng '把':")
            RadioButtonWithOptions(
                options = listOf("A. 我把书看。", "B. 我看把书。", "C. 我把书看完了。", "D. 我书把看完了。"),
                selectedAnswer = question10Answer,
                onAnswerChanged = { question10Answer = it }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Nút gửi bài
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)  // Đảm bảo nút luôn ở dưới cùng
        ) {
            Button(
                onClick = {
                    scope.launch {
                        // Kiểm tra các câu trả lời, nếu đúng thì cộng điểm
                        if (question1Answer == null || question2Answer == null || question3Answer == null ||
                            question4Answer == null || question5Answer == null || question6Answer == null ||
                            question7Answer == null || question8Answer == null || question9Answer == null ||
                            question10Answer == null) {
                            message = "❌ Bạn phải trả lời tất cả câu hỏi!"
                        } else {
                            // Tạo đối tượng PlacementTestModel với các câu trả lời
                            val testModel = PlacementTestModel(
                                question1Answer!!, question2Answer!!, question3Answer!!,
                                question4Answer!!, question5Answer!!, question6Answer!!,
                                question7Answer!!, question8Answer!!, question9Answer!!, question10Answer!!
                            )
                            val score = PlacementTestLogic().calculateScore(testModel)
                            val level = PlacementTestLogic().classifyLevel(score)

                            message = "Điểm của bạn: $score\nTrình độ: $level"
                            // Điều hướng đến màn hình kết quả với điểm số
                            navController.navigate("placement_result/$score/10")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gửi bài làm")
            }
        }
    }
}

@Composable
fun RadioButtonWithOptions(
    options: List<String>,
    selectedAnswer: String?,
    onAnswerChanged: (String) -> Unit
) {
    options.forEach { option ->
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(
                selected = selectedAnswer == option,
                onClick = { onAnswerChanged(option) }
            )
            Text(option)
        }
    }
}