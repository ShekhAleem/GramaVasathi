package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmStayDetailScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    val farmStay by viewModel.selectedFarmStay.collectAsState()

    farmStay?.let { stay ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stay.name, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Saffron,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                Surface(shadowElevation = 8.dp) {
                    Button(
                        onClick = { navController.navigate(Screen.Booking.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Saffron)
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Book This Stay — ₹${stay.pricePerNight}/night", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            },
            containerColor = Cream
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // ── Hero Image ──
                Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
                    AsyncImage(
                        model = stay.imageUrl,
                        contentDescription = stay.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.5f))))
                    )
                    // Verified
                    if (stay.isVerified) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Leaf)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Verified Host", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    // ── Name & Rating ──
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stay.name, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = TextBrown)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Saffron, modifier = Modifier.size(14.dp))
                                Text("${stay.village}, ${stay.district}", fontSize = 13.sp, color = MediumBrown)
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFAA00), modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(2.dp))
                                Text("${stay.rating}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                            }
                            Text("${stay.reviewCount} reviews", fontSize = 11.sp, color = MediumBrown)
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // Host
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Hosted by ${stay.hostName}", fontSize = 13.sp, color = MediumBrown, fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.height(16.dp))

                    // ── Description ──
                    Text("About this Stay", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                    Spacer(Modifier.height(8.dp))
                    Text(stay.description, fontSize = 14.sp, color = MediumBrown, lineHeight = 22.sp)

                    Spacer(Modifier.height(20.dp))

                    // ── Host Readiness Score ──
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = if (stay.hostReadinessScore >= 80) Leaf.copy(0.1f) else Saffron.copy(0.1f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Host Readiness Score", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextBrown)
                                Text(
                                    "${stay.hostReadinessScore}%",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = if (stay.hostReadinessScore >= 80) Leaf else Saffron
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { stay.hostReadinessScore / 100f },
                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                color = if (stay.hostReadinessScore >= 80) Leaf else Saffron,
                                trackColor = LightCream
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                if (stay.hostReadinessScore >= 80) "✅ This host is well-prepared for city guests"
                                else "ℹ️ Host is still completing the readiness checklist",
                                fontSize = 12.sp,
                                color = MediumBrown
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Farm Activities ──
                    Text("Farm Experiences", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        stay.activities.forEach { activity ->
                            ActivityPill(activity)
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Amenities ──
                    if (stay.amenities.isNotEmpty()) {
                        Text("Amenities", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                        Spacer(Modifier.height(10.dp))
                        stay.amenities.chunked(2).forEach { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                row.forEach { amenity ->
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Leaf, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(amenity, fontSize = 13.sp, color = MediumBrown)
                                    }
                                }
                                if (row.size < 2) Spacer(Modifier.weight(1f))
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun ActivityPill(activity: String) {
    val emoji = when {
        activity.contains("Milking") -> "🐄"
        activity.contains("Plowing") -> "🌾"
        activity.contains("Cooking") -> "🍳"
        activity.contains("Bird") -> "🦅"
        activity.contains("Pottery") -> "🎨"
        activity.contains("Fishing") -> "🐟"
        activity.contains("Bullock") -> "🐂"
        activity.contains("Music") -> "🎶"
        activity.contains("Farming") -> "🌱"
        else -> "🏡"
    }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LightCream),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 16.sp)
            Spacer(Modifier.width(6.dp))
            Text(activity, fontSize = 12.sp, color = TextBrown, fontWeight = FontWeight.Medium)
        }
    }
}
