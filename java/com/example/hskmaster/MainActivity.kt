package com.example.hskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hskmaster.screens.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav = rememberNavController()

            NavHost(
                navController = nav,
                startDestination = "login"
            ) {

                composable("login") {
                    LoginScreen(
                        onLoginSuccess = { username ->
                            nav.navigate("home/$username") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onGoToRegister = {
                            nav.navigate("register")
                        }
                    )
                }


                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = { nav.popBackStack() },
                        onBackToLogin = { nav.popBackStack() },
                        navController = nav
                    )
                }

                composable("home/{username}") { backStack ->
                    val username = backStack.arguments?.getString("username") ?: ""
                    HomeScreen(
                        username = username,
                        nav = nav
                    )
                }

                composable("quiz_tab") {
                    QuizTab(navController = nav)
                }


                composable("hsk_test_list") {
                    HskTestListScreen(navController = nav)
                }

                composable("quiz_screen/{level}") { backStackEntry ->
                    val level = backStackEntry.arguments?.getString("level") ?: "HSK 1"
                    QuizScreen(
                        level = level,
                        topicId = "",
                        navController = nav
                    )
                }

                composable(
                    route = "quiz_screen/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.StringType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getString("level") ?: "1"
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                    QuizScreen(
                        level = level,
                        topicId = topicId,
                        navController = nav
                    )
                }


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


                composable("placement_result/{score}/{total}") { backStackEntry ->
                    val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                    val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 10

                    ResultScreen(
                        score = score,
                        total = total,
                        onRetry = {
                            nav.navigate("placement_test") {
                                popUpTo("placement_test") { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        onBack = {
                            nav.navigate("home/user") {                    // ← Sửa chính ở đây
                                popUpTo("home/{username}") {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }


                composable("hsk_result/{score}/{total}/{level}") { backStackEntry ->
                    val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                    val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 15
                    val level = backStackEntry.arguments?.getString("level") ?: "HSK 1"

                    QuizResultScreen(
                        correctAnswers = score,
                        totalQuestions = total,
                        level = level,
                        navController = nav
                    )
                }

                composable(
                    route = "topicDetail/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""

                    TopicDetailScreen(
                        level = level,
                        topicId = topicId,
                        navController = nav
                    )
                }

                composable(
                    route = "vocabulary/{level}/{topicId}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType },
                        navArgument("topicId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1
                    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""

                    VocabularyLevelScreen(
                        level = level,
                        topicId = topicId,
                        navController = nav
                    )
                }


                composable(
                    route = "vocabulary_level/{level}",
                    arguments = listOf(
                        navArgument("level") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getInt("level") ?: 1
                    VocabularyLevelScreen(
                        level = level,
                        topicId = "",
                        navController = nav
                    )
                }

                composable(
                    route = "vocabulary_category/{category}",
                    arguments = listOf(
                        navArgument("category") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val category = backStackEntry.arguments?.getString("category") ?: ""
                    VocabularyCategoryScreen(
                        category = category,
                        navController = nav
                    )
                }

                composable("favorites") {
                    FavoritesScreen(navController = nav)
                }

                // ================= CHANGE PASSWORD =================
                composable("change_password/{username}") { backStack ->
                    val username = backStack.arguments?.getString("username") ?: ""
                    ChangePasswordScreen(
                        username = username,
                        onDone = { nav.popBackStack() }
                    )
                }

                composable("view_user_info/{username}") { backStack ->
                    val username = backStack.arguments?.getString("username") ?: ""
                    ViewUserInfoScreen(
                        username = username,
                        onBack = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}