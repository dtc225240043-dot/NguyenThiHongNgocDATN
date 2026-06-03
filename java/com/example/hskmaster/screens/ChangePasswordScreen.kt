package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.hskmaster.data.local.DatabaseProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    username: String,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var messageType by remember { mutableStateOf("") } // "success" or "error"
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🔐 Đổi mật khẩu") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
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
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thẻ nhập mật khẩu
            Card(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = androidx.compose.ui.Modifier.padding(20.dp)) {
                    Text(
                        text = "Nhập mật khẩu mới",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1976D2),
                        modifier = androidx.compose.ui.Modifier.padding(bottom = 16.dp)
                    )

                    // Mật khẩu mới
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Mật khẩu mới") },
                        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color(0xFF1976D2)
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))

                    // Xác nhận mật khẩu
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Xác nhận mật khẩu") },
                        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color(0xFF1976D2)
                            )
                        },
                        singleLine = true
                    )
                }
            }

            // Messages
            if (message.isNotEmpty()) {
                Box(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (messageType == "success") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = message,
                        color = if (messageType == "success") Color(0xFF2E7D32) else Color(0xFFC62828),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
            }

            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

            // Nút cập nhật
            Button(
                onClick = {
                    if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        message = "❌ Vui lòng nhập đầy đủ thông tin"
                        messageType = "error"
                    } else if (newPassword != confirmPassword) {
                        message = "❌ Mật khẩu không trùng khớp"
                        messageType = "error"
                    } else if (newPassword.length < 4) {
                        message = "❌ Mật khẩu phải có ít nhất 4 ký tự"
                        messageType = "error"
                    } else {
                        isLoading = true
                        scope.launch {
                            try {
                                val db = DatabaseProvider.getDatabase(context)
                                db.userDao().changePassword(username, newPassword)
                                message = "✅ Đổi mật khẩu thành công!"
                                messageType = "success"
                                newPassword = ""
                                confirmPassword = ""
                            } catch (e: Exception) {
                                message = "❌ Lỗi: ${e.message}"
                                messageType = "error"
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    disabledContainerColor = Color(0xFFB0BEC5)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = androidx.compose.ui.Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("💾 Cập nhật mật khẩu", color = Color.White)
                }
            }

            Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))

            // Nút quay lại
            OutlinedButton(
                onClick = onDone,
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("◀ Quay lại")
            }
        }
    }
}