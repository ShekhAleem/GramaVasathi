package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gramavasathi.app.data.model.ChecklistItem
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostTrainingScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    val checklistItems by viewModel.checklistItems.collectAsState()
    val score by viewModel.hostReadinessScore.collectAsState()

    val categories = checklistItems.groupBy { it.category }
    var currentStep by remember { mutableIntStateOf(0) }
    val categoryList = categories.keys.toList()
    val isLastStep = currentStep == categoryList.size - 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Host Training", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Leaf,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Cream
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Score Header ──
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🏠 Host Readiness Score", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                    Spacer(Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    score >= 80 -> Leaf
                                    score >= 50 -> Saffron
                                    else -> Clay
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$score%", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color.White)
                            Text(
                                when {
                                    score >= 80 -> "Ready! ✅"
                                    score >= 50 -> "Good 🙂"
                                    else -> "Needs Work"
                                },
                                fontSize = 11.sp,
                                color = Color.White.copy(0.9f)
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { score / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = when {
                            score >= 80 -> Leaf
                            score >= 50 -> Saffron
                            else -> Clay
                        },
                        trackColor = LightCream
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${checklistItems.count { it.isCompleted }} of ${checklistItems.size} items completed",
                        fontSize = 12.sp,
                        color = MediumBrown
                    )
                }
            }

            // ── Step Indicator ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                categoryList.forEachIndexed { index, category ->
                    val isCurrent = index == currentStep
                    val isDone = index < currentStep
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                when {
                                    isDone -> Leaf
                                    isCurrent -> Saffron
                                    else -> LightCream
                                }
                            )
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(
                "Step ${currentStep + 1} of ${categoryList.size}: ${categoryList.getOrNull(currentStep) ?: ""}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MediumBrown,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(8.dp))

            // ── Checklist Items for current step ──
            val currentCategory = categoryList.getOrNull(currentStep) ?: ""
            val currentItems = categories[currentCategory] ?: emptyList()

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(currentItems) { item ->
                    ChecklistCard(item = item, onToggle = { viewModel.toggleChecklistItem(item.id) })
                }
            }

            // ── Navigation Buttons ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MediumBrown)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Previous")
                    }
                }
                Button(
                    onClick = {
                        if (!isLastStep) currentStep++ else navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isLastStep) Leaf else Saffron)
                ) {
                    Text(if (isLastStep) "Finish Training" else "Next Step")
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        if (isLastStep) Icons.Default.CheckCircle else Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChecklistCard(item: ChecklistItem, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isCompleted) Leaf.copy(0.08f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Leaf,
                    uncheckedColor = MediumBrown
                )
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = if (item.isCompleted) Leaf else TextBrown
                )
                Text(
                    item.description,
                    fontSize = 12.sp,
                    color = MediumBrown,
                    lineHeight = 18.sp
                )
            }
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (item.isCompleted) Leaf else Saffron.copy(0.15f)
            ) {
                Text(
                    "+${item.points}pts",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (item.isCompleted) Color.White else Saffron,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
