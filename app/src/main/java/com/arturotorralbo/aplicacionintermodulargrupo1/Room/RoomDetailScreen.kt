package com.arturotorralbo.aplicacionintermodulargrupo1.Room


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.components.FeatureList
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.components.GalleryRow
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.components.RentButton
import com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.SelectRoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Login
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Payment
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.login.LoginViewModel


@Composable
fun RoomDetailScreen(navController: NavController,
                     roomViewModel: RoomViewModel,
                     selectRoomViewModel: SelectRoomViewModel,
                     tokenManager: TokenManager,
                     loginViewModel: LoginViewModel = hiltViewModel()
) {

    val selectedRoom by roomViewModel.selectedRoom.collectAsState()

    if (selectedRoom == null) {
        Text("No hay información de la habitación seleccionada", color = Color.Red)
        return
    }

    val room = selectedRoom!!

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = room.imagenes.firstOrNull(),
            contentDescription = "RoomDetail - Header Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(30.dp)
                .size(40.dp)
                .background(Color(0xFF278498), shape = RoundedCornerShape(50)),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

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
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = room.tipoHabitacion,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color(0xFF278498),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row {
                        Text(
                            text = "Características y servicios: ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    FeatureList(
                        numPersonas = room.numPersonas,
                        tamanyo = room.tamanyo,
                        servicios = room.servicios
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = room.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Gallería",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    GalleryRow(
                        imageList = room.imagenes,
                        roomViewModel = roomViewModel,
                        navController = navController
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    RentButton(price = "${room.precio}€") {
                        selectRoomViewModel.updateTipoHabitacion(room.tipoHabitacion)
                        selectRoomViewModel.updateIdHabitacion(room.idHabitacion)
                        selectRoomViewModel.updatePrecio(room.precio)
                        navController.navigate(Payment)
                    }
                }
            }
        }
    }
}

