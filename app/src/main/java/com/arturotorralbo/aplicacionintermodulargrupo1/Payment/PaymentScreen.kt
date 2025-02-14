package com.arturotorralbo.aplicacionintermodulargrupo1.Payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.SelectRoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileViewModel
import kotlinx.coroutines.delay

val PrimaryColor = Color(0xFF278498)

@Composable
fun PaymentScreen(
    startDate: String = "01/01/2025",
    endDate: String = "02/01/2025",
    numberOfGuests: Int = 1,
    roomType: String = "Habitación Estándar",
    totalPrice: Double = 200.0,
    userName: String = "Álvaro Maya",
    userEmail: String = "alvaromayacrespo@gmail.com",
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    selectRoomViewModel: SelectRoomViewModel,
    profileViewModel: ProfileViewModel
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardCVV by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var cardExpirationDate by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val user = profileViewModel.user.value


    fun isPaymentValid(): Boolean {
        return cardNumber.length == 16 &&
                cardCVV.length == 3 &&
                cardExpirationDate.length == 5 &&
                "/" in cardExpirationDate &&
                cardHolderName.isNotBlank()
    }

    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(errorMessage!!) }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {

            },
            title = { Text("Reserva Creada") },
            text = { Text("Tu reserva ha sido creada con éxito.") }
        )

        LaunchedEffect(Unit) {
            delay(3000)
            showDialog = false
            onNavigateToHome()
        }
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
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = "https://i.imgur.com/3Iwt2Xm.png",
                        contentDescription = "Progress Bar",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(16.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen de la Reserva",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryColor
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Fecha de inicio:", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                        Text(startDate, style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Fecha de fin:", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                        Text(endDate, style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Número de noches:",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Text("${calculateNights(startDate, endDate)}", style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Número de huéspedes:",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Text("$numberOfGuests", style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Tipo de habitación:",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(roomType, style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Extras:",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Text("${selectRoomViewModel.extrasInt.value} * 20 €/Noche", style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Precio final:", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                        Text(
                            text = "${"%.2f".format(totalPrice)} €",
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Información del Usuario",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nombre:", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                        Text(profileViewModel.user.value.name, style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Correo electrónico:", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                        Text(profileViewModel.user.value.email, style = TextStyle(fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Datos de la Tarjeta de Crédito",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = cardNumber,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() } && it.length <= 16) cardNumber = it
                            },
                            placeholder = { Text("Número de tarjeta") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextField(
                                value = cardCVV,
                                onValueChange = {
                                    if (it.all { char -> char.isDigit() } && it.length <= 3) cardCVV = it
                                },
                                placeholder = { Text("CVV") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp)
                            )
                            TextField(
                                value = cardExpirationDate,
                                onValueChange = { input ->
                                    if (input.length <= 5 && input.all { it.isDigit() || it == '/' }) {
                                        cardExpirationDate = when {
                                            input.length > 2 && input[2] != '/' -> input.substring(0, 2) + "/" + input.substring(2)
                                            else -> input
                                        }
                                    }
                                },
                                placeholder = { Text("MM/AA") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp)
                            )
                        }
                        TextField(
                            value = cardHolderName,
                            onValueChange = { cardHolderName = it },
                            placeholder = { Text("Nombre del titular") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
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
                    Button(
                        onClick = {
                            if (isPaymentValid()) {
                                selectRoomViewModel.crearReserva(
                                    userName = user.name,
                                    userEmail = user.email,
                                    onSuccess = { showDialog = true },
                                    onError = { error -> errorMessage = error }
                                )
                            } else {
                                errorMessage = "Datos de tarjeta inválidos."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = PrimaryColor),
                        modifier = Modifier.fillMaxWidth(0.8f).height(60.dp).clip(RoundedCornerShape(16.dp)),
                        enabled = isPaymentValid()
                    ) {
                        Text(text = "Pagar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

fun calculateNights(startDate: String, endDate: String): Int {
    return try {
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val start = formatter.parse(startDate)
        val end = formatter.parse(endDate)
        if (start != null && end != null && start.before(end)) {
            val diff = end.time - start.time
            (diff / (1000 * 60 * 60 * 24)).toInt()
        } else {
            0
        }
    } catch (e: Exception) {
        0
    }
}
