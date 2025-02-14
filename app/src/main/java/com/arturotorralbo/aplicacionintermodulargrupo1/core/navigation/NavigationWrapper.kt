package com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturotorralbo.aplicacionintermodulargrupo1.Home.HomeScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Payment.PaymentScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.GalleryDetailScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.RoomDetailScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.SelectRoomScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom.SelectRoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.login.presentation.LoginScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.presentation.ProfileScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.register.presentation.RegisterScreen
import com.arturotorralbo.aplicacionintermodulargrupo1.search.presentation.SearchScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val roomViewModel: RoomViewModel = hiltViewModel()
    val selectRoomViewModel: SelectRoomViewModel = hiltViewModel()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController)
        }
        composable<Register> {
            RegisterScreen(navController)
        }
        composable<Login> {
            LoginScreen(navController)
        }
        composable<RoomDetail> {
            RoomDetailScreen(navController, roomViewModel, selectRoomViewModel, tokenManager = tokenManager, )
        }
        composable<Profile>{
            ProfileScreen(navController)
        }
        composable<GalleryDetail> {
            GalleryDetailScreen(navController, roomViewModel)
        }
        composable<Search> {
            SearchScreen(
                onNavigateToSelectRoom = { start, end, guests ->
                    selectRoomViewModel.updateDetails(start, end, guests)
                    navController.navigate(SelectRoom)
                },
                navController = navController,
                viewModel = selectRoomViewModel
            )
        }
        composable<SelectRoom> {
            SelectRoomScreen(
                startDate = selectRoomViewModel.startDate.value,
                endDate = selectRoomViewModel.endDate.value,
                numberOfGuests = selectRoomViewModel.numberOfGuests.value,
                onBackClick = { navController.popBackStack() },
                viewModel = roomViewModel,
                navController = navController,
                selectRoomViewModel = selectRoomViewModel
            )
        }
        composable<Payment> {
            PaymentScreen(
                startDate = selectRoomViewModel.startDate.value,
                endDate = selectRoomViewModel.endDate.value,
                numberOfGuests = selectRoomViewModel.numberOfGuests.value,
                roomType = selectRoomViewModel.tipoHabitacion.value,
                totalPrice = ((selectRoomViewModel.precio.value + (selectRoomViewModel.extrasInt.value * 20)) * selectRoomViewModel.calculateNights(selectRoomViewModel.startDate.value, selectRoomViewModel.endDate.value)),
                selectRoomViewModel = selectRoomViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Home) { inclusive = true }
                    }
                }
            )
        }
    }
}