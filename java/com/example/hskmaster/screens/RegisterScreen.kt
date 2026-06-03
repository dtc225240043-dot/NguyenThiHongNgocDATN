package com.example.hskmaster.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hskmaster.data.local.DatabaseProvider
import com.example.hskmaster.data.local.entity.UserEntity
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    navController: NavController // Thêm NavController để điều hướng
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var goal by remember { mutableStateOf("") }
    var goalError by remember { mutableStateOf<String?>(null) }
    var currentLevel by remember { mutableStateOf("") }
    var currentLevelError by remember { mutableStateOf<String?>(null) }
    var studyTime by remember { mutableStateOf("") }
    var studyTimeError by remember { mutableStateOf<String?>(null) }
    var ageGroup by remember { mutableStateOf("") }
    var ageGroupError by remember { mutableStateOf<String?>(null) }
    var preferredLearningMethod by remember { mutableStateOf("") }
    var preferredLearningMethodError by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("📝 Đăng ký", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = {
                username = it
                if (usernameError != null && it.isNotEmpty()) usernameError = null
            },
            label = { Text("Tên tài khoản") },
            isError = usernameError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!usernameError.isNullOrEmpty()) {
            Text(
                text = usernameError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError != null && it.isNotEmpty() && it.length >= 8) passwordError = null
            },
            label = { Text("Mật khẩu") },
            isError = passwordError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!passwordError.isNullOrEmpty()) {
            Text(
                text = passwordError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Các trường còn lại (goal, currentLevel, studyTime, ageGroup, preferredLearningMethod)
        TextField(
            value = goal,
            onValueChange = {
                goal = it
                if (goalError != null && it.isNotEmpty()) goalError = null
            },
            label = { Text("Mục tiêu học") },
            isError = goalError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!goalError.isNullOrEmpty()) {
            Text(
                text = goalError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = currentLevel,
            onValueChange = {
                currentLevel = it
                if (currentLevelError != null && it.isNotEmpty()) currentLevelError = null
            },
            label = { Text("Trình độ hiện tại") },
            isError = currentLevelError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!currentLevelError.isNullOrEmpty()) {
            Text(
                text = currentLevelError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = studyTime,
            onValueChange = {
                studyTime = it
                if (studyTimeError != null && it.isNotEmpty()) studyTimeError = null
            },
            label = { Text("Thời gian học mỗi ngày") },
            isError = studyTimeError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!studyTimeError.isNullOrEmpty()) {
            Text(
                text = studyTimeError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = ageGroup,
            onValueChange = {
                ageGroup = it
                if (ageGroupError != null && it.isNotEmpty()) ageGroupError = null
            },
            label = { Text("Độ tuổi") },
            isError = ageGroupError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!ageGroupError.isNullOrEmpty()) {
            Text(
                text = ageGroupError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = preferredLearningMethod,
            onValueChange = {
                preferredLearningMethod = it
                if (preferredLearningMethodError != null && it.isNotEmpty()) preferredLearningMethodError = null
            },
            label = { Text("Cách học yêu thích") },
            isError = preferredLearningMethodError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (!preferredLearningMethodError.isNullOrEmpty()) {
            Text(
                text = preferredLearningMethodError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Validate all fields are not empty
                var hasError = false
                
                if (username.isBlank()) {
                    usernameError = "Tên tài khoản không được bỏ trống"
                    hasError = true
                }
                
                if (password.isBlank()) {
                    passwordError = "Mật khẩu không được bỏ trống"
                    hasError = true
                } else if (password.length < 8) {
                    passwordError = "Mật khẩu tối thiểu 8 kí tự"
                    hasError = true
                }
                
                if (goal.isBlank()) {
                    goalError = "Mục tiêu học không được bỏ trống"
                    hasError = true
                }
                
                if (currentLevel.isBlank()) {
                    currentLevelError = "Trình độ hiện tại không được bỏ trống"
                    hasError = true
                }
                
                if (studyTime.isBlank()) {
                    studyTimeError = "Thời gian học không được bỏ trống"
                    hasError = true
                }
                
                if (ageGroup.isBlank()) {
                    ageGroupError = "Độ tuổi không được bỏ trống"
                    hasError = true
                }
                
                if (preferredLearningMethod.isBlank()) {
                    preferredLearningMethodError = "Cách học yêu thích không được bỏ trống"
                    hasError = true
                }
                
                if (hasError) {
                    return@Button
                }

                scope.launch {
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

                        // Thực hiện điều hướng đến PlacementTestScreen sau khi đăng ký thành công
                        navController.navigate("placement_test")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đăng ký")
        }

        TextButton(onClick = onBackToLogin) {
            Text("Đã có tài khoản? Đăng nhập")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(message)
    }
}