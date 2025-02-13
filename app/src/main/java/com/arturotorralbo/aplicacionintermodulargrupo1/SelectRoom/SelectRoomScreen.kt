package com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.components.RoomCard
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.RoomDetail
import java.text.SimpleDateFormat
import java.util.Locale

val PrimaryColor = Color(0xFF278498)

@Composable
fun SelectRoomScreen(
    startDate: String,
    endDate: String,
    numberOfGuests: Int,
    onBackClick: () -> Unit,
    viewModel: RoomViewModel = hiltViewModel(),
    navController: NavController,
) {
    val rooms by viewModel.rooms.collectAsState()

    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(date)!!)
    }

    LaunchedEffect(startDate, endDate, numberOfGuests) {
        val formattedStartDate = formatDate(startDate)
        val formattedEndDate = formatDate(endDate)
        println("La fecha: $formattedStartDate")
        viewModel.fetchRoomsByCriteria(formattedStartDate, formattedEndDate, numberOfGuests, false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp))
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = PrimaryColor,
                        shape = RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp)
                    )
                    .padding(horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver atrás",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 3.dp)
                        .clickable { onBackClick() }
                )

                Spacer(modifier = Modifier.width(30.dp))

                AsyncImage(
                    model = "https://i.imgur.com/pgExtpm.png",
                    contentDescription = "Logo de la App",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(240.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = "https://i.imgur.com/NQ8qqge.png",
                    contentDescription = "Progress Bar",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(70.dp)
                        .padding(10.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(7f)
                    .padding(horizontal = 16.dp)
            ) {
                items(rooms) { room ->
                    RoomCard(room) {
                        viewModel.selectRoom(room)
                        navController.navigate(RoomDetail)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                    .background(PrimaryColor)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fechas: $startDate → $endDate",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Huéspedes: $numberOfGuests",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
