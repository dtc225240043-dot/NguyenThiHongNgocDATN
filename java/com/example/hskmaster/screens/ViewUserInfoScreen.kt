package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hskmaster.data.local.DatabaseProvider
import com.example.hskmaster.data.local.entity.UserEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewUserInfoScreen(
    username: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var userInfo by remember { mutableStateOf<UserEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var messageType by remember { mutableStateOf("") } // "success" or "error"

    // Fields edit
    var editGoal by remember { mutableStateOf("") }
    var editCurrentLevel by remember { mutableStateOf("") }
    var editStudyTime by remember { mutableStateOf("") }
    var editAgeGroup by remember { mutableStateOf("") }
    var editPreferredMethod by remember { mutableStateOf("") }

    // Load user info từ DB
    LaunchedEffect(username) {
        scope.launch {
            try {
                println("🔍 ViewUserInfo: Loading user: $username")
                val db = DatabaseProvider.getDatabase(context)
                val user = db.userDao().checkUserExists(username)
                println("🔍 ViewUserInfo: Query result: $user")
                userInfo = user
                if (user != null) {
                    editGoal = user.goal
                    editCurrentLevel = user.currentLevel
                    editStudyTime = user.studyTime
                    editAgeGroup = user.ageGroup
                    editPreferredMethod = user.preferredLearningMethod
                    println("✅ ViewUserInfo: User loaded successfully")
                } else {
                    println("⚠️ ViewUserInfo: User not found in database")
                }
                isLoading = false
            } catch (e: Exception) {
                println("❌ ViewUserInfo: Exception - ${e.message}")
                e.printStackTrace()
                message = "❌ Lỗi: ${e.message}"
                messageType = "error"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("✏️ Cập nhật thông tin tài khoản") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (userInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                // Username (read-only)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "👤 Tên tài khoản (không thể thay đổi)",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = userInfo!!.username,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Editable fields
                EditFieldCard(
                    label = "🎯 Mục tiêu học",
                    value = editGoal,
                    onValueChange = { editGoal = it }
                )

                EditFieldCard(
                    label = "📚 Trình độ hiện tại",
                    value = editCurrentLevel,
                    onValueChange = { editCurrentLevel = it }
                )

                EditFieldCard(
                    label = "⏰ Thời gian học mỗi ngày",
                    value = editStudyTime,
                    onValueChange = { editStudyTime = it }
                )

                EditFieldCard(
                    label = "🎂 Độ tuổi",
                    value = editAgeGroup,
                    onValueChange = { editAgeGroup = it }
                )

                EditFieldCard(
                    label = "💡 Cách học yêu thích",
                    value = editPreferredMethod,
                    onValueChange = { editPreferredMethod = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Message
                if (message.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (messageType == "success") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = message,
                            color = if (messageType == "success") Color(0xFF2E7D32) else Color(0xFFC62828),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Save Button
                    Button(
                        onClick = {
                            isSaving = true
                            scope.launch {
                                try {
                                    val updatedUser = userInfo!!.copy(
                                        goal = editGoal,
                                        currentLevel = editCurrentLevel,
                                        studyTime = editStudyTime,
                                        ageGroup = editAgeGroup,
                                        preferredLearningMethod = editPreferredMethod
                                    )
                                    val db = DatabaseProvider.getDatabase(context)
                                    db.userDao().updateUserInfo(updatedUser)
                                    message = "✅ Cập nhật thông tin thành công!"
                                    messageType = "success"
                                    userInfo = updatedUser
                                } catch (e: Exception) {
                                    message = "❌ Lỗi: ${e.message}"
                                    messageType = "error"
                                } finally {
                                    isSaving = false
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            disabledContainerColor = Color(0xFFB0BEC5)
                        ),
                        enabled = !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("💾 Lưu", color = Color.White)
                        }
                    }

                    // Back Button
                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("◀ Quay lại")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("❌ Không thể tải thông tin", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun EditFieldCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text("Nhập thông tin...") },
                singleLine = false,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}








