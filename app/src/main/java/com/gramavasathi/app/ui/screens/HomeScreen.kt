package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    Scaffold(
        containerColor = Cream
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header Banner ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        Brush.verticalGradient(listOf(Saffron, SaffronDark))
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🌾  Grama-Vasathi", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("ಗ್ರಾಮ ವಸತಿ", fontSize = 16.sp, color = Color.White.copy(0.85f))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Discover authentic Rural Karnataka.\nLive the Matti-Vasane experience.",
                        fontSize = 13.sp,
                        color = Color.White.copy(0.8f),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Quick Nav Cards ──
            Text(
                "  Explore Grama-Vasathi",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextBrown,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NavCard(
                    icon = Icons.Default.Search,
                    label = "Find Farm Stays",
                    color = Saffron,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate(Screen.Explore.route) }

                NavCard(
                    icon = Icons.Default.School,
                    label = "Host Training",
                    color = Leaf,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate(Screen.HostTraining.route) }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NavCard(
                    icon = Icons.Default.MenuBook,
                    label = "Cultural Guide",
                    color = Clay,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate(Screen.CulturalGuide.route) }

                NavCard(
                    icon = Icons.Default.Star,
                    label = "Top Rated",
                    color = Color(0xFFD4A017),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate(Screen.Explore.route) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Activities Filter Teaser ──
            Text(
                "  Popular Activities",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextBrown,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            val activities = listOf("🐄 Cow Milking", "🌾 Organic Farming", "🍳 Local Cooking",
                "🐟 Fishing", "🎨 Pottery", "🦅 Birdwatching")
            activities.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { activity ->
                        ActivityChip(
                            label = activity,
                            modifier = Modifier.weight(1f)
                        ) {
                            val actName = activity.substringAfter(" ")
                            viewModel.selectActivity(actName)
                            navController.navigate(Screen.Explore.route)
                        }
                    }
                    if (rowItems.size < 3) repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Impact Stats ──
            ImpactStatsSection()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun NavCard(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextBrown, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ActivityChip(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightCream),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        )
    }
}

@Composable
fun ImpactStatsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Leaf),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("🌱  Our Impact", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem("120+", "Rural\nHosts", Color.White)
                StatItem("2,400+", "City\nGuests", Color.White)
                StatItem("18", "Districts\nCovered", Color.White)
                StatItem("₹45L+", "Farmer\nIncome", Color.White)
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
        Text(label, fontSize = 10.sp, color = color.copy(0.8f), textAlign = TextAlign.Center)
    }
}
