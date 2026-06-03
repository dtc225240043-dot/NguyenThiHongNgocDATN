package com.example.hskmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun VocabTab(
    navController: NavController
) {
    var searchText by remember { mutableStateOf("") }

    val hskItems = listOf(
        HskUiItem(1, "HSK 1", "150 từ", "📗", Color(0xFFEAF8E6), Color(0xFF2FA84F)),
        HskUiItem(2, "HSK 2", "300 từ", "📘", Color(0xFFEAF4FF), Color(0xFF2478D4)),
        HskUiItem(3, "HSK 3", "600 từ", "📙", Color(0xFFFFF4DF), Color(0xFFD87900)),
        HskUiItem(4, "HSK 4", "1200 từ", "📕", Color(0xFFF2EAFE), Color(0xFF6B45B8)),
        HskUiItem(5, "HSK 5", "2500 từ", "📒", Color(0xFFE8FAF5), Color(0xFF159C91)),
        HskUiItem(6, "HSK 6", "5000 từ", "📓", Color(0xFFFFEAF0), Color(0xFFD93B63))
    )

    val categoryItems = listOf(
        CategoryUiItem("Du lịch", "20+ từ", "✈", Color(0xFF58C947), Color(0xFF2AA815)),
        CategoryUiItem("Nhà hàng", "20+ từ", "🍽", Color(0xFFFFB32C), Color(0xFFF09500)),
        CategoryUiItem("Công nghệ", "20+ từ", "🖥", Color(0xFF4898F5), Color(0xFF1874D2)),
        CategoryUiItem("Kinh doanh", "20+ từ", "📊", Color(0xFF8956E8), Color(0xFF5A2DBB)),
        CategoryUiItem("Trường học", "20+ từ", "🎓", Color(0xFF12B8A6), Color(0xFF008F81))
    )

    val filteredHskItems = hskItems.filter { it.title.contains(searchText, ignoreCase = true) }
    val filteredCategoryItems = categoryItems.filter { it.title.contains(searchText, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8FF))
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "📚", fontSize = 15.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Từ vựng",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25252C),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // SEARCH BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f).height(64.dp),
                placeholder = { Text("Tìm kiếm từ vựng...", color = Color(0xFF8B8798), fontSize = 17.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF7D778B))
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE1DDEA),
                    unfocusedBorderColor = Color(0xFFE1DDEA),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Card(
                modifier = Modifier.size(64.dp).clickable { },
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("▽", fontSize = 14.sp, color = Color(0xFF6F45B8), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // HSK SECTION
        BigWhiteSection {
            SectionHeader(icon = "📗", title = "Học theo cấp độ HSK")
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filteredHskItems.forEach { item ->
                    HskCard(
                        item = item,
                        onClick = {
                            navController.navigate("vocabulary_level/${item.level}")
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CATEGORY SECTION - ĐÃ SỬA
        BigWhiteSection {
            SectionHeader(icon = "💼", title = "Từ vựng chuyên ngành")
            Spacer(modifier = Modifier.height(18.dp))

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                filteredCategoryItems.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        rowItems.forEach { item ->
                            CategoryCard(
                                item = item,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    // Navigate đến màn hình từ vựng theo category
                                    navController.navigate("vocabulary_category/${item.title}")
                                }
                            )
                        }
                        if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        // FAVORITES SHORTCUT
        BigWhiteSection {
            Row(modifier = Modifier.fillMaxWidth().clickable { navController.navigate("favorites") }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⭐", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Từ vựng yêu thích", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Text(text = "Xem danh sách từ vựng bạn đã đánh dấu", color = Color(0xFF6B6B70), fontSize = 13.sp)
                }
                Text(text = ">", color = Color(0xFF6B45B8), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// ==================== DATA CLASS ====================
private data class HskUiItem(
    val level: Int,
    val title: String,
    val totalWords: String,
    val icon: String,
    val backgroundColor: Color,
    val mainColor: Color
)

private data class CategoryUiItem(
    val title: String,
    val subTitle: String,
    val icon: String,
    val startColor: Color,
    val endColor: Color
)

// ==================== HSK CARD ====================
@Composable
private fun HskCard(item: HskUiItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(118.dp)
            .height(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = item.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 18.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = item.mainColor)

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(item.mainColor.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.icon, fontSize = 20.sp)
            }

            Text(text = item.totalWords, fontSize = 17.sp, color = Color(0xFF24222A))
        }
    }
}

// ==================== CATEGORY CARD ====================
@Composable
private fun CategoryCard(
    item: CategoryUiItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(138.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(listOf(item.startColor, item.endColor)),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.icon, fontSize = 34.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222128),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.subTitle,
                fontSize = 14.sp,
                color = Color(0xFF4F4B5A),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ==================== HELPER ====================
@Composable
private fun BigWhiteSection(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), content = content)
    }
}

@Composable
private fun SectionHeader(icon: String, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = icon, fontSize = 26.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF29272E)
        )
    }
}