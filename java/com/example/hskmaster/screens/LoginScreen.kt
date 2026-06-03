package com.example.hskmaster.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hskmaster.data.local.DatabaseProvider
import com.example.hskmaster.data.local.SessionManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onGoToRegister: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("🔐 Đăng nhập", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên tài khoản") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    val db = DatabaseProvider.getDatabase(context)
                    val user = db.userDao().login(username, password)

                    if (user != null) {
                        message = "✅ Đăng nhập thành công"
                        // Lưu session username để dùng ở các màn hình khác
                        SessionManager.saveUsername(context, username)
                        onLoginSuccess(username) // 🔥 QUAN TRỌNG
                    } else {
                        message = "❌ Sai tài khoản hoặc mật khẩu"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đăng nhập")
        }

        TextButton(onClick = onGoToRegister) {
            Text("Chưa có tài khoản? Đăng ký")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(message)
    }
}