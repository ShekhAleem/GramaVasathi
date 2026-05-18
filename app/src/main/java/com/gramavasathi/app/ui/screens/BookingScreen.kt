package com.gramavasathi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gramavasathi.app.ui.theme.*
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(navController: NavController, viewModel: GramaVasathiViewModel) {
    val farmStay by viewModel.selectedFarmStay.collectAsState()

    var guestName by remember { mutableStateOf("") }
    var checkIn by remember { mutableStateOf("") }
    var checkOut by remember { mutableStateOf("") }
    var guestCount by remember { mutableIntStateOf(1) }
    var errorMessage by remember { mutableStateOf("") }

    // Simulated available dates
    val availableDates = listOf(
        "Jun 14", "Jun 15", "Jun 16", "Jun 20", "Jun 21",
        "Jun 22", "Jun 27", "Jun 28", "Jun 29", "Jul 4",
        "Jul 5", "Jul 6", "Jul 10", "Jul 11", "Jul 12"
    )

    val nights = if (checkIn.isNotEmpty() && checkOut.isNotEmpty()) {
        val inIdx = availableDates.indexOf(checkIn)
        val outIdx = availableDates.indexOf(checkOut)
        if (outIdx > inIdx) outIdx - inIdx else 1
    } else 1

    val totalPrice = (farmStay?.pricePerNight ?: 0) * nights * guestCount

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Your Stay", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Stay Info Banner
            farmStay?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Saffron.copy(0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null, tint = Saffron, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(it.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextBrown)
                            Text("${it.village} • ₹${it.pricePerNight}/night", fontSize = 12.sp, color = MediumBrown)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Guest Name
            Text("Your Name", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextBrown)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = guestName,
                onValueChange = { guestName = it },
                placeholder = { Text("Enter your full name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Saffron) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Saffron),
                singleLine = true
            )

            Spacer(Modifier.height(20.dp))

            // Calendar – Check In
            Text("Select Check-In Date", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextBrown)
            Spacer(Modifier.height(8.dp))
            CalendarGrid(
                dates = availableDates,
                selectedDate = checkIn,
                disabledAfter = checkOut,
                onSelect = { checkIn = it }
            )

            Spacer(Modifier.height(16.dp))

            // Calendar – Check Out
            Text("Select Check-Out Date", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextBrown)
            Spacer(Modifier.height(8.dp))
            CalendarGrid(
                dates = availableDates,
                selectedDate = checkOut,
                disabledBefore = checkIn,
                onSelect = { checkOut = it }
            )

            Spacer(Modifier.height(20.dp))

            // Guest Count
            Text("Number of Guests", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextBrown)
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = { if (guestCount > 1) guestCount-- },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LightCream)
                ) { Icon(Icons.Default.Remove, contentDescription = null, tint = Saffron) }

                Text("$guestCount", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextBrown)

                IconButton(
                    onClick = { if (guestCount < 6) guestCount++ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LightCream)
                ) { Icon(Icons.Default.Add, contentDescription = null, tint = Saffron) }

                Text("guests", fontSize = 14.sp, color = MediumBrown)
            }

            Spacer(Modifier.height(24.dp))

            // Price Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Price Summary", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextBrown)
                    Spacer(Modifier.height(10.dp))
                    PriceLine("₹${farmStay?.pricePerNight} × $nights nights × $guestCount guests", "₹$totalPrice")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = LightCream)
                    PriceLine("Total", "₹$totalPrice", isBold = true)
                }
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        guestName.isBlank() -> errorMessage = "Please enter your name"
                        checkIn.isEmpty() -> errorMessage = "Please select a check-in date"
                        checkOut.isEmpty() -> errorMessage = "Please select a check-out date"
                        else -> {
                            viewModel.makeBooking(
                                farmStayId = farmStay?.id ?: "",
                                farmStayName = farmStay?.name ?: "",
                                guestName = guestName,
                                checkIn = checkIn,
                                checkOut = checkOut,
                                guests = guestCount,
                                totalPrice = totalPrice
                            )
                            navController.navigate(Screen.BookingConfirm.route)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Saffron)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Confirm Booking", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(8.dp))
            Text(
                "* Booking is simulated. No real payment required.",
                fontSize = 11.sp,
                color = MediumBrown,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun CalendarGrid(
    dates: List<String>,
    selectedDate: String,
    disabledBefore: String = "",
    disabledAfter: String = "",
    onSelect: (String) -> Unit
) {
    val rows = dates.chunked(5)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                row.forEach { date ->
                    val isSelected = date == selectedDate
                    val disabledB = disabledBefore.isNotEmpty() && dates.indexOf(date) <= dates.indexOf(disabledBefore)
                    val disabledA = disabledAfter.isNotEmpty() && dates.indexOf(date) >= dates.indexOf(disabledAfter)
                    val isDisabled = disabledB || disabledA
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when {
                                    isSelected -> Saffron
                                    isDisabled -> LightCream.copy(0.5f)
                                    else -> LightCream
                                }
                            )
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = if (isSelected) Color.Transparent else ClayLight.copy(0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = !isDisabled) { onSelect(date) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = when {
                                isSelected -> Color.White
                                isDisabled -> MediumBrown.copy(0.4f)
                                else -> TextBrown
                            }
                        )
                    }
                }
                // Fill empty slots
                repeat(5 - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun PriceLine(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = if (isBold) TextBrown else MediumBrown)
        Text(value, fontSize = 13.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = if (isBold) Saffron else TextBrown)
    }
}
