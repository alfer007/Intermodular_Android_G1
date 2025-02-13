package com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturotorralbo.aplicacionintermodulargrupo1.core.models.Room
import com.arturotorralbo.aplicacionintermodulargrupo1.core.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository
): ViewModel()  {

    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms: StateFlow<List<Room>> = _rooms.asStateFlow()

    private val _selectedRoom = MutableStateFlow<Room?>(null)
    val selectedRoom: StateFlow<Room?> = _selectedRoom.asStateFlow()

    private val _selectedImageIndex = MutableStateFlow(0) // 🔥 Nuevo estado para la imagen seleccionada
    val selectedImageIndex: StateFlow<Int> = _selectedImageIndex.asStateFlow()

    fun fetchRoomsByCriteria(startDate: String, endDate: String, numPersonas: Int, extraCama: Boolean) {
        viewModelScope.launch {
            try {
                _rooms.value = emptyList()
                val response = repository.getRoomsByCriteria(startDate, endDate, numPersonas, extraCama)
                _rooms.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun setSelectedImageIndex(index: Int) {
        _selectedImageIndex.value = index
    }
}