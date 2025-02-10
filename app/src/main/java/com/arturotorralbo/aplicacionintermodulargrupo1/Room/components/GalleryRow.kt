package com.arturotorralbo.aplicacionintermodulargrupo1.Room.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.GalleryDetail

@Composable
fun GalleryRow(
    imageList: List<String>,
    roomViewModel: RoomViewModel,
    navController: NavController
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        itemsIndexed(imageList) { index, imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagen de galería",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        if (imageList.isNotEmpty()) {
                            roomViewModel.setSelectedImageIndex(index)
                            navController.navigate(GalleryDetail)
                        }
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}