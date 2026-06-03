package com.example.hskmaster.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hskmaster.data.local.DatabaseProvider
import com.example.hskmaster.data.local.entity.UserEntity
import kotlinx.coroutines.launch

@Composable
fun UserInfoScreen(
    onSubmit: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var currentLevel by remember { mutableStateOf("") }
    var studyTime by remember { mutableStateOf("") }
    var ageGroup by remember { mutableStateOf("") }
    var preferredLearningMethod by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("📝 Thông tin cá nhân", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Tên tài khoản
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên tài khoản") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mật khẩu
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mục tiêu học
        TextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Mục tiêu học") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Trình độ hiện tại
        TextField(
            value = currentLevel,
            onValueChange = { currentLevel = it },
            label = { Text("Trình độ hiện tại") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Thời gian học mỗi ngày
        TextField(
            value = studyTime,
            onValueChange = { studyTime = it },
            label = { Text("Thời gian học mỗi ngày") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Độ tuổi
        TextField(
            value = ageGroup,
            onValueChange = { ageGroup = it },
            label = { Text("Độ tuổi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Cách học yêu thích
        TextField(
            value = preferredLearningMethod,
            onValueChange = { preferredLearningMethod = it },
            label = { Text("Cách học yêu thích") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    // Lưu thông tin vào cơ sở dữ liệu
                    val db = DatabaseProvider.getDatabase(context)
                    val existingUser = db.userDao().checkUserExists(username)

                    if (existingUser != null) {
                        message = "❌ Tài khoản đã tồn tại"
                    } else {
                        db.userDao().register(
                            UserEntity(
                                username = username,
                                password = password,
                                goal = goal,
                                currentLevel = currentLevel,
                                studyTime = studyTime,
                                ageGroup = ageGroup,
                                preferredLearningMethod = preferredLearningMethod
                            )
                        )
                        message = "✅ Đăng ký thành công"
                        onSubmit() // Quay lại màn hình chính sau khi đăng ký thành công
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gửi thông tin")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(message)
    }
}