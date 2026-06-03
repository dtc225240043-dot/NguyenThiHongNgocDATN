// StatsTab.kt
package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hskmaster.data.local.entity.UserStatsEntity
import com.example.hskmaster.viewmodel.StatsViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.dao.UserStatsDaoImpl
import com.example.hskmaster.data.repository.StatsRepository
import com.example.hskmaster.data.local.entity.UserEntity
import com.example.hskmaster.data.local.SessionManager
import kotlinx.coroutines.launch

@Composable
fun StatsTab() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val repository = remember { StatsRepository(UserStatsDaoImpl(db.userStatsDao())) }
    val viewModel = remember { StatsViewModel(repository) }

    val stats by viewModel.userStats.collectAsState()

    var currentLevelStr by remember { mutableStateOf("") }
    var goalStr by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Load user info using saved session username, fallback to first user
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val saved = SessionManager.getUsername(context)
                val user = if (!saved.isNullOrEmpty()) db.userDao().checkUserExists(saved) else db.userDao().getAnyUser()
                if (user != null) {
                    currentLevelStr = user.currentLevel
                    goalStr = user.goal
                }
            } catch (_: Exception) {
                // ignore
            }
        }
    }

    // Use LazyColumn to enable proper vertical scrolling inside NavHost/Scaffold
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Text(
                text = "Tiến độ học",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item { CurrentLevelCard(stats) }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item { StatsOverview(stats) }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item { LevelGoalBar(stats = stats, currentLevelStr = currentLevelStr, goalStr = goalStr) }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { MotivationalCard() }
    }
}

// ====================== COMPONENTS ======================

@Composable
fun CurrentLevelCard(stats: UserStatsEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "🐵", fontSize = 52.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text("Trình độ hiện tại", color = Color.Gray)
                Text(
                    text = "HSK${stats.level}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800)
                )
                Text("XP: ${stats.xp} • Cố lên! 💪", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun StatsOverview(stats: UserStatsEntity) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatCard("📚", "Bài đã học", "${stats.lessonsStudied}", Modifier.weight(1f))
        StatCard("🔥", "Ngày học", "${stats.totalDays} ngày", Modifier.weight(1f))
        StatCard("⏰", "Thời gian học", "${stats.studyTime / 60}h ${stats.studyTime % 60}p", Modifier.weight(1f))
    }
}

@Composable
fun StatCard(icon: String, title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color.Gray, fontSize = 13.sp)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun LevelGoalBar(stats: UserStatsEntity, currentLevelStr: String, goalStr: String) {
    Column {
        Text(
            text = "📊 Mục tiêu học",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Trình độ hiện tại (xanh lá)
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Trình độ hiện tại", fontSize = 13.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Hiển thị currentLevel từ UserEntity
                    val displayCurrentLevel = if (currentLevelStr.isNotEmpty()) {
                        try {
                            currentLevelStr.toIntOrNull()?.toString() ?: currentLevelStr
                        } catch (e: Exception) {
                            currentLevelStr
                        }
                    } else {
                        stats.level.toString()
                    }
                    
                    Text(
                        text = "HSK $displayCurrentLevel",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("📈", fontSize = 24.sp)
                }
            }

            // Mục tiêu học (xanh dương)
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mục tiêu học", fontSize = 13.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Hiển thị goal từ UserEntity
                    val displayGoal = if (goalStr.isNotEmpty()) {
                        try {
                            goalStr.toIntOrNull()?.toString() ?: goalStr
                        } catch (e: Exception) {
                            goalStr
                        }
                    } else {
                        "—"
                    }
                    
                    Text(
                        text = "HSK $displayGoal",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D47A1)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("🎯", fontSize = 24.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Thêm row hiển thị quiz đã làm
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("📝 Số quiz đã làm", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${stats.quizzesDone} quiz", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF57F17))
                }
                Text("🎖️", fontSize = 48.sp)
            }
        }
    }

    // 6-level HSK column chart with goals
    Spacer(modifier = Modifier.height(16.dp))
    val currLevelNum = currentLevelStr.toIntOrNull() ?: stats.level
    val goalNum = goalStr.toIntOrNull() ?: 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📊 Biểu đồ 6 cấp độ HSK", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                for (level in 1..6) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Determine bar height and color based on currentLevel and goal
                        val barHeight = 100.dp // fixed height for column
                        val bgColor = when {
                            level == currLevelNum -> Color(0xFF66BB6A) // green for current
                            level == goalNum -> Color(0xFF42A5F5) // blue for goal
                            else -> Color(0xFFE0E0E0) // light gray for others
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(barHeight)
                                .background(bgColor, shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("HSK $level", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier
                        .size(12.dp)
                        .background(Color(0xFF66BB6A), RoundedCornerShape(2.dp)))
                    Text("Trình độ hiện tại", fontSize = 11.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier
                        .size(12.dp)
                        .background(Color(0xFF42A5F5), RoundedCornerShape(2.dp)))
                    Text("Mục tiêu", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun HSKProgressSection(stats: UserStatsEntity) {
    Column {
        Text(
            text = "Tiến độ theo cấp độ HSK",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..6) {
                val isCompleted = i < stats.level
                val isCurrent = i == stats.level

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(85.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when {
                                    isCompleted -> Color(0xFF4CAF50)
                                    isCurrent -> Color(0xFFFF9800)
                                    else -> Color.LightGray
                                }
                            )
                    ) {
                        if (isCompleted) {
                            Text("✅", modifier = Modifier.align(Alignment.Center), fontSize = 22.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("HSK$i", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun MotivationalCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📘", fontSize = 32.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Học mỗi ngày một chút,\nkiến thức sẽ tích lũy mỗi ngày!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}