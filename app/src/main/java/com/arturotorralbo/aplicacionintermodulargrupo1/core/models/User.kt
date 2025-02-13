package com.arturotorralbo.aplicacionintermodulargrupo1.core.models

data class User(
    val name : String = "",
    val email: String,
    val password: String,
    val role: String="usuario",
    val phone: String = "",
    val adress: String = ""
    )

