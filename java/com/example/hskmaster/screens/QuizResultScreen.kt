package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.dao.UserStatsDaoImpl
import com.example.hskmaster.data.repository.StatsRepository
import com.example.hskmaster.viewmodel.StatsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    level: String,
    timeSpent: String = "02:15",
    streak: String = "1 ngày",
    newWords: String = "12 từ",
    speed: String = "Nhanh",
    navController: NavController
) {
    val percentage = if (totalQuestions > 0) (correctAnswers * 100) / totalQuestions else 0
    val hskLevel = level.replace("HSK ", "")

    val pastelGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF9ECD),
            Color(0xFF9BE6FF)
        )
    )

    Scaffold(containerColor = Color.Transparent) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pastelGradient)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "🎉 Chúc mừng!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Bạn đã hoàn thành bài kiểm tra $level",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Vòng tròn phần trăm
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = percentage / 100f,
                    modifier = Modifier.size(210.dp),
                    strokeWidth = 18.dp,
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$percentage%",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$correctAnswers / $totalQuestions câu đúng",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Tuyệt vời!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(32.dp))

            // Card HSK Warrior
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🐼", fontSize = 48.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("HSK WARRIOR", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("LV. $hskLevel", color = Color(0xFF6B4EFF), fontWeight = FontWeight.Medium)
                        LinearProgressIndicator(
                            progress = 0.64f,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            color = Color(0xFFFF8EC1)
                        )
                        Text("320 / 500 XP", fontSize = 13.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Stats
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("⏱️", timeSpent, "Thời gian")
                StatItem("🔥", streak, "Streak")
                StatItem("🌱", newWords, "Từ mới")
                StatItem("⚡", speed, "Tốc độ")
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Làm lại")
                }

                Button(
                    onClick = {
                        // SỬA Ở ĐÂY: Quay về màn hình QuizTab
                        navController.navigate("quiz_tab") {
                            popUpTo("quiz_tab") { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6B4EFF))
                ) {
                    Text("Học tiếp")
                }

                IconButton(onClick = { /* Share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Chia sẻ", tint = Color.White)
                }
            }

                Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // Update stats when screen is shown (must be inside the composable scope)
            val context = LocalContext.current
            val db = remember { AppDatabase.getDatabase(context) }
            val repository = remember { StatsRepository(UserStatsDaoImpl(db.userStatsDao())) }
            val statsViewModel = remember { StatsViewModel(repository) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                scope.launch {
                            // add XP based on correctAnswers (10 XP per correct answer)
                            statsViewModel.addXP(correctAnswers * 10)
                    // count this quiz as one quiz done
                    statsViewModel.addQuizzes(1)
                    // NOTE: we no longer auto-count a lesson here to avoid double-counting.
                    // If you want quizzes to also mark a lesson completed, pass the lesson id and
                    // check/insert into the CompletedLesson table similarly to TopicDetailScreen.
                    // parse timeSpent "MM:SS" to minutes
                    try {
                        val parts = timeSpent.split(":")
                        val minutes = parts.getOrNull(0)?.toIntOrNull() ?: 0
                        statsViewModel.addStudyTime(minutes)
                    } catch (_: Exception) {
                    }
                    // update streak (mark today)
                    statsViewModel.updateStreak()
                }
            }

        }

@Composable
private fun StatItem(icon: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 26.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Text(text = label, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.8f))
    }
}