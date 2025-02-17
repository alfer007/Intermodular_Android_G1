package com.arturotorralbo.aplicacionintermodulargrupo1.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Reserva
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Home
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileResult
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val user = profileViewModel.user.value
    var showDeleteDialog by remember { mutableStateOf(false) }
    val reservas by profileViewModel.reservas
    val updateState = remember { profileViewModel.updateState }
    val deleteState = remember { profileViewModel.deleteState }


    LaunchedEffect(Unit) {
        profileViewModel.fetchUserProfile()
        profileViewModel.fetchUserReservas()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 16.dp, top = 36.dp)
                .size(40.dp)
                .background(Color(0xFF278498), shape = RoundedCornerShape(50))
                .align(Alignment.TopStart)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = user.name,
                onValueChange = { profileViewModel.user.value = user.copy(name = it) },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = user.email,
                onValueChange = {},
                label = { Text("Email") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = user.password,
                onValueChange = { profileViewModel.user.value = user.copy(password = it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = user.phone,
                onValueChange = {
                    profileViewModel.user.value =
                        user.copy(phone = it.filter { char -> char.isDigit() })
                },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = user.adress,
                onValueChange = { profileViewModel.user.value = user.copy(adress = it) },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    profileViewModel.updateUser(
                        user.email,
                        user.name,
                        user.password,
                        user.phone,
                        user.adress,
                        user.role
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF278498))
            ) {
                Text(text = "Guardar cambios", color = Color.White)
            }

            when (updateState.value) {
                is ProfileResult.Loading -> CircularProgressIndicator()
                is ProfileResult.Success -> Text(
                    "Perfil actualizado correctamente",
                    color = Color.Green
                )

                is ProfileResult.Error -> Text(
                    (updateState.value as ProfileResult.Error).message,
                    color = Color.Red
                )

                else -> {}
            }


            Spacer(modifier = Modifier.height(16.dp))
            Text("Mis Reservas", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (reservas.isEmpty()) {
                Text("No tienes reservas activas", color = Color.Gray)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reservas) { reserva ->
                        ReservaItem(reserva)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    profileViewModel.closeSession()
                    navController.navigate(Home)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Gray)
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Eliminar Cuenta", color = Color.White)
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("¿Eliminar cuenta?") },
                    text = { Text("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                profileViewModel.deleteUser(user.email)
                                showDeleteDialog = false
                            }
                        ) {
                            Text("Sí, eliminar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDeleteDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            when (deleteState.value) {
                is ProfileResult.Loading -> CircularProgressIndicator()
                is ProfileResult.Success -> {
                    Text("Cuenta eliminada correctamente", color = Color.Green)
                    LaunchedEffect(Unit) {
                        navController.navigate("login")
                    }
                }

                is ProfileResult.Error -> Text(
                    (deleteState.value as ProfileResult.Error).message,
                    color = Color.Red
                )

                else -> {}
            }
        }
    }
}

@Composable
fun ReservaItem(reserva: Reserva) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column {
            Text("Habitación: ${reserva.tipoHabitacion}", fontWeight = FontWeight.Bold)
            Text("Fecha: ${reserva.fechaInicio} - ${reserva.fechaSalida}")
            Text("Precio: ${reserva.precio}€")
            Text("Personas: ${reserva.numPersonas}")
            Text("Extras: ${reserva.extras}")
        }
    }
}
