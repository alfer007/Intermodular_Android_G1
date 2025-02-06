package com.arturotorralbo.aplicacionintermodulargrupo1.core.models

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.compose.ui.input.pointer.PointerEventPass

data class User(
    val name : String,
    val email: String,
    val password: String,
    val role: String,
    val phone: String,
    val adress: String
    )
