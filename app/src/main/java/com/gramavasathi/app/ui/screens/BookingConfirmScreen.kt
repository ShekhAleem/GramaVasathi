package com.gramavasathi.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@Composable
fun BookingConfirmScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    val farmStay by viewModel.selectedFarmStay.collectAsState()

    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // Success Icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale.value)
                    .clip(CircleShape)
                    .background(Leaf),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Booking Confirmed! 🎉",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextBrown,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Your rural getaway is reserved.\nGet ready for the Matti-Vasane experience!",
                fontSize = 14.sp,
                color = MediumBrown,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(24.dp))

            // Booking Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Booking Details", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextBrown)
                    Spacer(Modifier.height(12.dp))
                    DetailRow(Icons.Default.Home, "Farm Stay", farmStay?.name ?: "")
                    Spacer(Modifier.height(8.dp))
                    DetailRow(Icons.Default.LocationOn, "Location", "${farmStay?.village}, ${farmStay?.district}")
                    Spacer(Modifier.height(8.dp))
                    DetailRow(Icons.Default.Person, "Host", farmStay?.hostName ?: "")
                    Spacer(Modifier.height(8.dp))
                    DetailRow(Icons.Default.Phone, "Contact Host", "Via app when confirmed")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Tips Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Saffron.copy(0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🌿 Pre-Visit Tips", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextBrown)
                    Spacer(Modifier.height(8.dp))
                    listOf(
                        "Carry light, full-sleeve cotton clothing",
                        "Bring insect repellent and sunscreen",
                        "Respect local customs & traditions",
                        "Read the Cultural Guide before you go"
                    ).forEach { tip ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("• ", color = Saffron, fontWeight = FontWeight.Bold)
                            Text(tip, fontSize = 12.sp, color = MediumBrown, lineHeight = 18.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.clearBookingResult()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Saffron)
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.CulturalGuide.route) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Leaf)
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Read Cultural Guide", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Saffron, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 11.sp, color = MediumBrown)
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextBrown)
        }
    }
}
