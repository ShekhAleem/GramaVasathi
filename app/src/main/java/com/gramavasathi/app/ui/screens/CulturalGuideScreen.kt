package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gramavasathi.app.data.model.SampleData
import com.gramavasathi.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturalGuideScreen(navController: NavController) {
    val guide = SampleData.culturalGuide

    val colors = listOf(Saffron, Leaf, Clay, Color(0xFF4A90D9), Color(0xFF9B59B6), Color(0xFF16A085), Color(0xFFE74C3C), Color(0xFF2C3E50))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cultural Guide for Guests", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Clay,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Cream
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Header Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.horizontalGradient(listOf(Clay, SaffronDark)))
                        .padding(20.dp)
                ) {
                    Column {
                        Text("🙏  ನಮಸ್ಕಾರ", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Welcome to rural Karnataka! This guide will help you be a respectful and mindful guest in our villages.",
                            fontSize = 13.sp,
                            color = Color.White.copy(0.9f),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                Text("Etiquette & Tips", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
            }

            itemsIndexed(guide) { index, (title, description) ->
                val emoji = when (title) {
                    "Greeting" -> "🙏"
                    "Dress Code" -> "👘"
                    "Footwear" -> "👡"
                    "Meal Time" -> "🍽️"
                    "Photography" -> "📷"
                    "Water" -> "💧"
                    "Noise" -> "🔇"
                    "Bargaining" -> "🤝"
                    else -> "ℹ️"
                }
                val color = colors[index % colors.size]
                CulturalCard(emoji = emoji, title = title, description = description, accentColor = color)
            }

            item {
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Leaf.copy(0.1f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🌾  A Note from the Host Community", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextBrown)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "We open our homes to you with open hearts. We may not speak fluent English, but our hospitality is genuine and deep-rooted. Please be patient, curious, and kind — and you will leave with memories of a lifetime.",
                            fontSize = 13.sp,
                            color = MediumBrown,
                            lineHeight = 21.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("— Your Grama-Vasathi Hosts", fontSize = 12.sp, color = Leaf, fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CulturalCard(emoji: String, title: String, description: String, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 22.sp)
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = accentColor)
                Spacer(Modifier.height(4.dp))
                Text(description, fontSize = 13.sp, color = MediumBrown, lineHeight = 20.sp)
            }
        }
    }
}
