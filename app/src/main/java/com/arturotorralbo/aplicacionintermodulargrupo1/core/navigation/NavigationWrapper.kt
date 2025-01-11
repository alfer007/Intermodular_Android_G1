package com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.GalleryDetailScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.RoomDetailScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.login.presentation.LoginScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.register.presentation.RegisterScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.search.presentation.SearchScreen


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val roomViewModel: RoomViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = RoomDetail) {

        composable<Register> {
            RegisterScreen { navController.navigate(Login) }
        }
        composable<Login> {
            LoginScreen { navController.navigate(Register) }
        }
        composable<RoomDetail> {
            RoomDetailScreen(navController, roomViewModel)
        }
        composable<GalleryDetail> {
            val galleryImages = roomViewModel.galleryImages.value ?: emptyList()

            GalleryDetailScreen(
                imageList = galleryImages,
                initialSelectedIndex = 0,
                onBack = { navController.popBackStack() }
            )

        }
        composable<Search> {
            SearchScreen()
        }
    }
}