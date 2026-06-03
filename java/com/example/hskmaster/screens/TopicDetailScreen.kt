package com.example.hskmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hskmaster.components.VideoPlayer
import com.example.hskmaster.data.JsonLoader
import com.example.hskmaster.data.model.LessonModel
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.dao.UserStatsDaoImpl
import com.example.hskmaster.data.repository.StatsRepository
import com.example.hskmaster.viewmodel.StatsViewModel
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun TopicDetailScreen(
    level: Int,
    topicId: String,
    navController: NavController
) {
    val context = LocalContext.current
    var lesson by remember { mutableStateOf<LessonModel?>(null) }

    LaunchedEffect(level, topicId) {
        val lessons = JsonLoader.loadLessons(context, "hsk$level.json")
        lesson = lessons.find { it.id == topicId }
    }

    if (lesson == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = lesson!!.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Video
            VideoPlayer(url = lesson!!.videoUrl)

            Spacer(modifier = Modifier.height(24.dp))

            // NÚT TỪ VỰNG
            Button(
                onClick = {
                    navController.navigate("vocabulary/$level/$topicId")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📚 Từ vựng")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NÚT NGỮ PHÁP
            Button(
                onClick = {
                    navController.navigate("grammar/$level/$topicId")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✍️ Ngữ pháp")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NÚT LÀM QUIZ
            Button(
                onClick = {
                    navController.navigate("quiz_screen/$level/$topicId")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔥 Làm Quiz")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NÚT HOÀN THÀNH BÀI - cập nhật stats: lessonsStudied++ (idempotent)
            val ctx = LocalContext.current
            val db = AppDatabase.getDatabase(ctx)
            val repo = StatsRepository(UserStatsDaoImpl(db.userStatsDao()))
            val statsVm = remember { StatsViewModel(repo) }
            val completedDao = remember { db.completedLessonDao() }
            val scope = rememberCoroutineScope()

            Button(
                onClick = {
                    scope.launch {
                        // check if this lesson already marked completed
                        val count = completedDao.countById(topicId)
                        if (count == 0) {
                            // mark completed
                            val sdf = SimpleDateFormat("yyyy-MM-dd")
                            val today = sdf.format(Date())
                            completedDao.insert(com.example.hskmaster.data.local.entity.CompletedLesson(lessonId = topicId, completedAt = today))

                            // update stats once
                            statsVm.addLessons(1)
                            statsVm.addXP(20)
                            statsVm.addStudyTime(10) // approximate 10 minutes
                            statsVm.updateStreak()

                            Toast.makeText(ctx, "Đã hoàn thành bài", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(ctx, "Bài này đã được hoàn thành trước đó", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("✅ Hoàn thành bài")
            }
        }
    }
}