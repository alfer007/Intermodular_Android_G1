package com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(): ViewModel()  {
    private val _galleryImages = MutableLiveData<List<String>>()
    val galleryImages: LiveData<List<String>> get() = _galleryImages

    fun setGalleryImages(images: List<String>) {
        _galleryImages.value = images
    }
}