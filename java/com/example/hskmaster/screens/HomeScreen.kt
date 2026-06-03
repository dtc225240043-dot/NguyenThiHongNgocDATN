package com.example.hskmaster.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hskmaster.components.AccountMenuDrawer
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    nav: NavController
) {

    val innerNav = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = innerNav.currentBackStackEntryAsState()
        .value?.destination?.route

    AccountMenuDrawer(
        username = username,
        onChangePasswordClick = {
            scope.launch { drawerState.close() }
            nav.navigate("change_password/$username")
        },
        onViewUserInfoClick = {
            scope.launch { drawerState.close() }
            nav.navigate("view_user_info/$username")
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("👋 Xin chào, $username") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        TextButton(onClick = {
                            nav.navigate("login") {
                                popUpTo("home/$username") { inclusive = true }
                            }
                        }) {
                            Text("Logout")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFF1976D2),
                        titleContentColor = androidx.compose.ui.graphics.Color.White,
                        navigationIconContentColor = androidx.compose.ui.graphics.Color.White,
                        actionIconContentColor = androidx.compose.ui.graphics.Color.White
                    )
                )
            },

            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = currentRoute?.startsWith("learn") == true, onClick = { innerNav.navigate("learn") }, label = { Text("Học") }, icon = {})
                    NavigationBarItem(selected = currentRoute?.startsWith("vocab") == true, onClick = { innerNav.navigate("vocab") }, label = { Text("Từ vựng") }, icon = {})
                    NavigationBarItem(selected = currentRoute?.startsWith("quiz_tab") == true, onClick = { innerNav.navigate("quiz_tab") }, label = { Text("Quiz") }, icon = {})
                    NavigationBarItem(selected = currentRoute?.startsWith("stats") == true, onClick = { innerNav.navigate("stats") }, label = { Text("Stats") }, icon = {})
                    NavigationBarItem(selected = currentRoute?.startsWith("chat") == true, onClick = { innerNav.navigate("chat") }, label = { Text("AI") }, icon = {})
                }
            }
        ) { padding ->

            NavHost(
                navController = innerNav,
                startDestination = "learn",
                modifier = Modifier.padding(padding)
            ) {

                composable("learn") { HomeTab(innerNav) }

                composable("vocab") { VocabTab(navController = innerNav) }

                composable("quiz_tab") { QuizTab(navController = innerNav) }

                composable("stats") { StatsTab() }
                composable("chat") { ChatTab() }

                // ==================== ROUTES ====================
                composable("topicList/{level}") { backStack ->
                    val level = backStack.arguments?.getString("level")?.toIntOrNull() ?: 1
                    TopicListScreen(level = level, navController = innerNav)
                }
                // ==================== PLACEMENT TEST - TEST TỔNG HỢP ====================

// ==================== HSK TEST LIST ====================

                composable("topicDetail/{level}/{topicId}") { backStack ->
                    val level = backStack.arguments?.getString("level")?.toIntOrNull() ?: 1
                    val topicId = backStack.arguments?.getString("topicId") ?: ""
                    TopicDetailScreen(level = level, topicId = topicId, navController = innerNav)
                }

                composable("vocabulary_level/{level}/{topicId?}") { backStackEntry ->  // ? = optional
                    val level = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 1
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""

                    VocabularyLevelScreen(level = level, topicId = topicId, navController = innerNav)
                }

                // Từ vựng theo bài học cụ thể (từ TopicDetail)
                composable(
                    route = "vocabulary/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                    VocabularyLevelScreen(   // Phiên bản chi tiết theo bài
                        level = level,
                        topicId = topicId,
                        navController = innerNav
                    )
                }

                // Grammar
                composable(
                    route = "grammar/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                    GrammarScreen(hskLevel = level, topicId = topicId, navController = innerNav)
                }

                // Quiz
                composable(
                    route = "quiz_screen/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.StringType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getString("level") ?: "1"
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                    QuizScreen(level = level, topicId = topicId, navController = innerNav)
                }

                // Kết quả quiz
                composable(
                    route = "hsk_result/{score}/{total}/{level}/{timeSpent}",
                    arguments = listOf(
                        navArgument("score") { type = NavType.IntType },
                        navArgument("total") { type = NavType.IntType },
                        navArgument("level") { type = NavType.StringType },
                        navArgument("timeSpent") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val score = backStackEntry.arguments?.getInt("score") ?: 0
                    val total = backStackEntry.arguments?.getInt("total") ?: 15
                    val level = backStackEntry.arguments?.getString("level") ?: "HSK 1"
                    val timeSpent = backStackEntry.arguments?.getString("timeSpent") ?: "02:15"

                    QuizResultScreen(
                        correctAnswers = score,
                        totalQuestions = total,
                        level = level,
                        timeSpent = timeSpent,
                        navController = innerNav
                    )
                }

                // ==================== HSK TEST LIST (Fix crash) ====================
                composable("hsk_test_list") {
                    HskTestListScreen(navController = innerNav)
                }

                // ==================== ROUTE CHO TAB TỪ VỰNG (HSK LEVEL) ====================
                composable(
                    route = "vocabulary_level/{level}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1

                    VocabularyLevelScreen(
                        level = level,
                        topicId = "",           // Không cần topicId khi xem theo level
                        navController = innerNav   // hoặc nav tùy theo cấu trúc của bạn
                    )
                }

                // ==================== QUIZ THEO HSK LEVEL (Fix lỗi) ====================
                composable(
                    route = "quiz_screen/{level}",
                    arguments = listOf(
                        navArgument("level") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getString("level") ?: "HSK 1"

                    QuizScreen(
                        level = level,
                        navController = innerNav,      // ← Sửa thành innerNav
                        topicId = null
                    )
                }
                //tuvung theo chuyennganh
                composable(
                    route = "vocabulary_category/{category}",
                    arguments = listOf(navArgument("category") { type = NavType.StringType })
                ) { backStackEntry ->
                    val category = backStackEntry.arguments?.getString("category") ?: "Du lịch"

                    VocabularyCategoryScreen(
                        category = category,
                        navController = innerNav     // ← Đây là sửa chính
                    )
                }

                // ================= FAVORITES =================
                composable("favorites") {
                    FavoritesScreen(navController = innerNav)
                }

                // ================= PLACEMENT TEST =================
                composable("placement_test") {
                    PlacementTestScreen(
                        navController = nav,
                        onTestCompleted = { result ->
                            val resultParts = result.split(":")
                            val score = resultParts.getOrNull(0) ?: "0"
                            val level = resultParts.getOrNull(1) ?: "Unknown"
                            nav.navigate("test_result/$score/$level")
                        }
                    )
                }
                //quiz screen

                // Sau này thêm rouimport androidx.navigation.NavType
                //import androidx.navigation.compose.NavHost
                //import androidx.navigation.compose.composable
                //import androidx.navigation.navArgument
                //import androidx.navigation.compose.rememberNavControllerte cho Result nếu cần
                // composable("hsk_result/{score}/{total}/{level}/{timeSpent}") { ...



            }
        }
    }

}