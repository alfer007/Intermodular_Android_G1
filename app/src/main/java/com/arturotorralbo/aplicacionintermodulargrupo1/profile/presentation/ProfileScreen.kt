package com.arturotorralbo.aplicacionintermodulargrupo1.profile.presentation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Home
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileResult
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val user = profileViewModel.user.value
    var showDeleteDialog by remember { mutableStateOf(false) }

    val updateState = remember { profileViewModel.updateState }
    val deleteState = remember { profileViewModel.deleteState }

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserProfile()
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
            onValueChange = { profileViewModel.user.value = user.copy(phone = it.filter { char -> char.isDigit() }) },
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
            onClick = { profileViewModel.updateUser(user.email, user.name, user.password, user.phone, user.adress, user.role) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF278498))
        ) {
            Text(text = "Guardar cambios", color = Color.White)
        }

        when (updateState.value) {
            is ProfileResult.Loading -> CircularProgressIndicator()
            is ProfileResult.Success -> Text("Perfil actualizado correctamente", color = Color.Green)
            is ProfileResult.Error -> Text((updateState.value as ProfileResult.Error).message, color = Color.Red)
            else -> {}
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
            is ProfileResult.Error -> Text((deleteState.value as ProfileResult.Error).message, color = Color.Red)
            else -> {}
        }
        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(3) {
                Box(
                    modifier = Modifier
                        .size(150.dp, 100.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Reserva vacía", color = Color.Black)
                }
            }
        }
    }
}
