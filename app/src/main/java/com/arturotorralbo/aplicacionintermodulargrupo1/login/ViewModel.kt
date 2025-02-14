package com.arturotorralbo.aplicacionintermodulargrupo1.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.apiServices.UserApiServices
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.User
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
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
    private val _fromPayment = MutableStateFlow(false)
    val fromPayment: StateFlow<Boolean> = _fromPayment.asStateFlow()

    fun setFromPayment(value: Boolean) {
        _fromPayment.value = value
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = LoginResult.Loading
            try {
                val response = userApiServices.checkUser(User(email = email, password = password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        tokenManager.saveToken(body.token)
                        tokenManager.saveEmail(email)
                        loginState.value = LoginResult.Success
                    }
                } else {
                    loginState.value = LoginResult.Error("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                loginState.value = LoginResult.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}