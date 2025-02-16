package com.arturotorralbo.aplicacionintermodulargrupo1.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.ReservaApiService
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.UserApiServices
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Reserva
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userApiServices: UserApiServices,
    private val reservaApiService: ReservaApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var user = mutableStateOf(User(
        email = "",
        name = "",
        password = "",
        role = "",
        phone = "",
        adress = ""
    ))
        private set
    var reservas = mutableStateOf<List<Reserva>>(emptyList())
        private set
    suspend fun  fetchUserProfile() {
        viewModelScope.launch {

            val token = tokenManager.getToken()
            val email = tokenManager.getEmail()

            if (token.isNullOrEmpty() || email.isNullOrEmpty()) {
                println("Token o Email nulos")
                return@launch
            }

            try {
                val response = userApiServices.getUser(email, "Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        user.value = userResponse.copy(
                            name = userResponse.name ?: "",
                            email = userResponse.email ?: "",
                            password = userResponse.password ?: "",
                            role = userResponse.role ?: "",
                            phone = userResponse.phone ?: "",
                            adress = userResponse.adress ?: ""

                        )
                    }
                } else {
                    println("Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error al obtener el perfil: ${e.localizedMessage}")
            }
        }
    }

    fun fetchUserReservas() {
        viewModelScope.launch {
            val email = tokenManager.getEmail() ?: return@launch
            try {
                val filters = mapOf("cliente.email" to email)
                val response = reservaApiService.getFilteredReservas(filters)
                if (response.isSuccessful) {
                    reservas.value = response.body() ?: emptyList()
                } else {
                    println("Error al obtener reservas: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error al obtener reservas: ${e.localizedMessage}")
            }
        }
    }


    var updateState = mutableStateOf<ProfileResult>(ProfileResult.Idle)
        private set

    var deleteState = mutableStateOf<ProfileResult>(ProfileResult.Idle)
        private set

    fun updateUser(email: String, name: String, password: String, phone: String, address: String, role: String) {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch

            updateState.value = ProfileResult.Loading
            try {
                val user = User(
                    name = name,
                    email = email,
                    password = password,
                    phone = phone,
                    adress = address,
                    role = role
                )
                val response = userApiServices.updateUser(email, "Bearer $token", user)

                if (response.isSuccessful) {
                    updateState.value = ProfileResult.Success
                } else {
                    updateState.value = ProfileResult.Error("Error al actualizar el perfil")
                }
            } catch (e: Exception) {
                updateState.value = ProfileResult.Error("Error: ${e.localizedMessage}")
            }
        }
    }
    fun closeSession() {
        tokenManager.clearToken()
        tokenManager.clearEmail()
    }

    fun deleteUser(email: String) {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch

            deleteState.value = ProfileResult.Loading
            try {
                val response = userApiServices.deleteUser(email, "Bearer $token")
                if (response.isSuccessful) {
                    tokenManager.clearToken()
                    deleteState.value = ProfileResult.Success
                } else {
                    deleteState.value = ProfileResult.Error("Error al eliminar la cuenta")
                }
            } catch (e: Exception) {
                deleteState.value = ProfileResult.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}

sealed class ProfileResult {
    object Idle : ProfileResult()
    object Loading : ProfileResult()
    object Success : ProfileResult()
    data class Error(val message: String) : ProfileResult()
}