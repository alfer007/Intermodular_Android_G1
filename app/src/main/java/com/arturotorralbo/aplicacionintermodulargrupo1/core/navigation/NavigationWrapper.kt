package com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturotorralbo.aplicacionintermodulargrupo1.login.presentation.LoginScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.register.presentation.RegisterScreen


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Register) {

        composable<Register> {
            RegisterScreen{navController.navigate(Login)}
        }
        composable<Login> {
            LoginScreen()
        }
    }

}