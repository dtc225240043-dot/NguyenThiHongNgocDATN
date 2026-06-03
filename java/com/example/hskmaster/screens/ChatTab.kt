package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String = ""
)

@Composable
fun ChatTab() {
    var messages by remember {
        mutableStateOf(listOf(
            ChatMessage("👋 Xin chào! Tôi là AI HSK Master", false)
        ))
    }

    var input by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8BBD9), // Pastel pink
                        Color(0xFFBBDEFB)  // Pastel blue
                    )
                )
            )
    ) {
        // ==================== HEADER ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF6A1B9A),
                            Color(0xFF8E24AA)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "🤖 HSK AI Assistant",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hỏi bất cứ điều gì về tiếng Trung",
                    fontSize = 13.sp,
                    color = Color(0xFFE1BEE7)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ==================== QUICK OPTIONS ====================
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            QuickButton("📚 Từ vựng") {
                input = "Dạy tôi 10 từ vựng HSK 1 phổ biến"
            }

            QuickButton("📖 Ngữ pháp") {
                input = "Giải thích ngữ pháp cơ bản tiếng Trung"
            }

            QuickButton("🗣️ Hội thoại") {
                input = "Luyện hội thoại tiếng Trung cơ bản"
            }

            QuickButton("✍️ Viết") {
                input = "Giúp tôi viết câu tiếng Trung"
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ==================== CHAT MESSAGES ====================
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }
            
            if (isLoading) {
                item {
                    LoadingBubble()
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ==================== INPUT SECTION ====================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
                    placeholder = { Text("Nhập câu hỏi...", color = Color.Gray) },
                    singleLine = false,
                    maxLines = 3,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Button(
                    onClick = {
                        if (input.isEmpty() || isLoading) return@Button

                        val userMsg = input
                        messages = messages + ChatMessage(userMsg, true)
                        input = ""
                        isLoading = true

                        scope.launch {
                            try {
                                val aiResponse = `ChatGPTApi.kt`.getResponse(userMsg)
                                messages = messages + ChatMessage(aiResponse, false)
                            } catch (e: Exception) {
                                messages = messages + ChatMessage(
                                    "❌ Có lỗi xảy ra: ${e.message}",
                                    false
                                )
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8E24AA),
                        disabledContainerColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Gửi",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// =============================
// 🎨 CHAT BUBBLE
// =============================
@Composable
fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(
                    if (message.isUser) {
                        Color(0xFF8E24AA)
                    } else {
                        Color(0xFFE8E8E8)
                    },
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .padding(14.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) Color.White else Color(0xFF333333),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

// =============================
// 💬 LOADING BUBBLE
// =============================
@Composable
fun LoadingBubble() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(
                    Color(0xFFE8E8E8),
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(14.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                Color(0xFF999999),
                                RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
        }
    }
}

// =============================
// 🔘 QUICK BUTTON
// =============================
@Composable
fun QuickButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF8E24AA)
        )
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}