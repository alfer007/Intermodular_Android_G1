package com.arturotorralbo.aplicacionintermodulargrupo1.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.UserApiServices
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userApiServices: UserApiServices
) : ViewModel() {

    var registerState = mutableStateOf<RegisterResult>(RegisterResult.Idle)
        private set

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            registerState.value = RegisterResult.Loading
            try {
                val user = User(name = name, email = email, password = password, role = "usuario", phone = "", adress = "")
                val response = userApiServices.createUser(user)
                if (response.isSuccessful) {
                    registerState.value = RegisterResult.Success
                } else {
                    registerState.value = RegisterResult.Error("Error al registrar usuario")
                }
            } catch (e: Exception) {
                registerState.value = RegisterResult.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}

sealed class RegisterResult {
    object Idle : RegisterResult()
    object Loading : RegisterResult()
    object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}