package com.arturotorralbo.aplicacionintermodulargrupo1.SelectRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SelectRoomViewModel : ViewModel() {
    val startDate = mutableStateOf("")
    val endDate = mutableStateOf("")
    val numberOfGuests = mutableStateOf(1)

    fun updateStartDate(date: String) {
        startDate.value = date
    }

    fun updateEndDate(date: String) {
        endDate.value = date
    }

    fun updateNumberOfGuests(guests: Int) {
        numberOfGuests.value = guests
    }

    fun updateDetails(start: String, end: String, guests: Int) {
        updateStartDate(start)
        updateEndDate(end)
        updateNumberOfGuests(guests)
    }
}

