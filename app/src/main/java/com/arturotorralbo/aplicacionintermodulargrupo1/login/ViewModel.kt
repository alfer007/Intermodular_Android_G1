package com.arturotorralbo.aplicacionintermodulargrupo1.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.UserApiServices
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.profile.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userApiServices: UserApiServices,
    private val tokenManager: TokenManager
) : ViewModel() {

    var loginState = mutableStateOf<LoginResult>(LoginResult.Idle)
        private set

    fun loginUser(email: String, password: String,profileViewModel: ProfileViewModel) {
        viewModelScope.launch {
            loginState.value = LoginResult.Loading
            try {
                val response = userApiServices.checkUser(User(email = email, password = password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.token != null) {
                        tokenManager.saveToken(body.token)
                        tokenManager.saveEmail(email)
                        loginState.value = LoginResult.Success
                        profileViewModel.fetchUserProfile()

                    } else {
                        loginState.value = LoginResult.Error("Respuesta inválida del servidor.")
                    }
                } else {
                    loginState.value = LoginResult.Error("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                loginState.value = LoginResult.Error("Error: ${e.localizedMessage}")
            }
        }
    }
    fun resetLoginState() {
        loginState.value = LoginResult.Idle
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}