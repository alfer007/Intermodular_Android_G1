package com.arturotorralbo.aplicacionintermodulargrupo1.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Home
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Register
import com.arturotorralbo.aplicacionintermodulargrupo1.login.LoginResult
import com.arturotorralbo.aplicacionintermodulargrupo1.login.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginState = remember { loginViewModel.loginState }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))

        BodySection(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = it },
            onLoginClick = { loginViewModel.loginUser(email, password) }
        )

        Spacer(modifier = Modifier.height(40.dp))
        FooterSection(onClick = { navController.navigate(Register) })

        when (loginState.value) {
            is LoginResult.Loading -> CircularProgressIndicator()
            is LoginResult.Success -> {

                LaunchedEffect(Unit) {
                    navController.navigate(Home)
                }
            }
            is LoginResult.Error -> {
                Text(
                    text = (loginState.value as LoginResult.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            else -> {}
        }
    }
}

@Composable
fun HeaderSection() {
    Text(
        text = "Login",
        style = MaterialTheme.typography.headlineMedium.copy(
            color = Color(0xFF278498),
            fontWeight = FontWeight.Bold
        )
    )
    Text(
        text = "Login to access your accout.",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray,
        modifier = Modifier.padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun BodySection(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    onLoginClick: () -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email Address") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    PasswordField(
        password = password,
        onPasswordChange = onPasswordChange,
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = onPasswordVisibilityChange
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF278498))
    ) {
        Text(text = "Login")
    }
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FooterSection(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don’t have an account? ",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        TextButton(onClick = onClick) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
