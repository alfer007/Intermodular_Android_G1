package com.arturotorralbo.aplicacionintermodulargrupo1.search.presentation

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.ErrorColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SearchScreen(onNavigateToSelectRoom: (String, String, Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    var showMenu by remember { mutableStateOf(false) }
    var numberOfGuests by remember { mutableStateOf(1) }

    var isInvalidDate by remember { mutableStateOf(false) }
    val today = Calendar.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = "https://i.imgur.com/LqYN2bG.jpg",
            contentDescription = "Search Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    AsyncImage(
                        model = "https://i.imgur.com/wY5g4Cb.png",
                        contentDescription = "Progress Bar",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(70.dp)
                            .padding(16.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.05f)
                    )
                    AsyncImage(
                        model = "https://imgur.com/L3IkhzC.png",
                        contentDescription = "Search Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f)
                    )
                    Button(
                        onClick = {
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val startCalendar = Calendar.getInstance()
                                    startCalendar.set(year, month, dayOfMonth)
                                    startDate = dateFormat.format(startCalendar.time)
                                    val selectedDate = startCalendar.time

                                    if (selectedDate.before(Calendar.getInstance().time) || startCalendar.before(today)) {
                                        isInvalidDate = true
                                    } else {
                                        startDate = dateFormat.format(selectedDate)
                                        isInvalidDate = false
                                    }

                                    DatePickerDialog(
                                        context,
                                        { _, endYear, endMonth, endDayOfMonth ->
                                            val endCalendar = Calendar.getInstance()
                                            endCalendar.set(endYear, endMonth, endDayOfMonth)
                                            endDate = dateFormat.format(endCalendar.time)
                                            val endSelectedDate = endCalendar.time

                                            if (endSelectedDate.before(selectedDate) || startCalendar.before(today)) {
                                                isInvalidDate = true
                                            } else {
                                                endDate = dateFormat.format(endSelectedDate)
                                                isInvalidDate = false
                                            }
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(1.dp, color = if (isInvalidDate) ErrorColor else Color(0xFF278498), shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                            .background(color = if (isInvalidDate) ErrorColor else Color(0xFF278498)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                                    "$startDate → $endDate"
                                } else {
                                    "Seleccionar fechas"
                                },
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Ícono de calendario",
                                tint = Color.White
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(1.dp)
                            .background(Color.White)
                            .align(Alignment.CenterHorizontally)
                    )

                    Button(
                        onClick = { showMenu = !showMenu },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(1.dp, color = Color(0xFF278498), shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                            .background(Color(0xFF278498)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Número de huéspedes: $numberOfGuests",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "Ícono de personas",
                                tint = Color.White
                            )
                        }
                    }

                    if (showMenu) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, color = Color(0xFF278498), shape = RoundedCornerShape(15.dp))
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.White)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Button(
                                    onClick = { if (numberOfGuests > 1) numberOfGuests-- },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF278498)
                                    )
                                ) {
                                    Text(text = "-", color = Color.White, fontSize = 18.sp)
                                }
                                Text(
                                    text = "$numberOfGuests",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF278498)
                                )
                                Button(
                                    onClick = { numberOfGuests++ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF278498)
                                    )
                                ) {
                                    Text(text = "+", color = Color.White, fontSize = 18.sp)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                                onNavigateToSelectRoom(startDate, endDate, numberOfGuests)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        enabled = startDate.isNotEmpty() && endDate.isNotEmpty() && !isInvalidDate,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (startDate.isNotEmpty() && endDate.isNotEmpty() && !isInvalidDate) {
                                Color(0xFF278498)
                            } else {
                                Color.Gray
                            }
                        )
                    ) {
                        Text(
                            text = "Consultar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "Consultar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}