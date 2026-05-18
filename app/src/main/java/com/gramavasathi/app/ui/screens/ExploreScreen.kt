package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gramavasathi.app.data.model.FarmStay
import com.gramavasathi.app.data.model.SampleData
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    val farmStays by viewModel.filteredFarmStays.collectAsState()
    val selectedActivity by viewModel.selectedActivity.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Ensure list is populated
    LaunchedEffect(Unit) {
        if (farmStays.isEmpty()) viewModel.loadFarmStays()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Farm Stays", fontWeight = FontWeight.Bold) },
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
        containerColor = Cream
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Search Bar ──
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("Search by village or district...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Saffron) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Saffron,
                    unfocusedBorderColor = ClayLight
                ),
                singleLine = true
            )

            // ── Activity Filter Chips ──
            val allActivities = listOf("All") + SampleData.activities
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                allActivities.forEach { activity ->
                    val isSelected = if (activity == "All") selectedActivity == null else selectedActivity == activity
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            viewModel.selectActivity(if (activity == "All") null else activity)
                        },
                        label = { Text(activity, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Saffron,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Results Count ──
            Text(
                "  ${farmStays.size} stays found",
                fontSize = 13.sp,
                color = MediumBrown,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // ── Farm Stay List ──
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Saffron)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(farmStays) { farmStay ->
                        FarmStayCard(farmStay = farmStay) {
                            viewModel.selectFarmStay(farmStay)
                            navController.navigate(Screen.FarmStayDetail.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FarmStayCard(farmStay: FarmStay, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = farmStay.imageUrl,
                    contentDescription = farmStay.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.4f))))
                )
                // Verified badge
                if (farmStay.isVerified) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Leaf
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(3.dp))
                            Text("Verified", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                // Price tag
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Saffron
                ) {
                    Text(
                        "₹${farmStay.pricePerNight}/night",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Content
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(farmStay.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBrown)
                        Text(
                            "${farmStay.village}, ${farmStay.district}",
                            fontSize = 12.sp,
                            color = MediumBrown
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFAA00), modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(2.dp))
                        Text("${farmStay.rating}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextBrown)
                        Text(" (${farmStay.reviewCount})", fontSize = 11.sp, color = MediumBrown)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    farmStay.description,
                    fontSize = 12.sp,
                    color = MediumBrown,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(10.dp))

                // Activities
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    farmStay.activities.take(4).forEach { activity ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = LightCream
                        ) {
                            Text(
                                activity,
                                fontSize = 10.sp,
                                color = MediumBrown,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Host Readiness Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Host Readiness", fontSize = 11.sp, color = MediumBrown)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LinearProgressIndicator(
                            progress = { farmStay.hostReadinessScore / 100f },
                            modifier = Modifier.width(80.dp).height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = if (farmStay.hostReadinessScore >= 80) Leaf else Saffron,
                            trackColor = LightCream
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("${farmStay.hostReadinessScore}%", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                            color = if (farmStay.hostReadinessScore >= 80) Leaf else Saffron)
                    }
                }
            }
        }
    }
}
