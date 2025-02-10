package com.arturotorralbo.aplicacionintermodulargrupo1.Room


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.arturotorralbo.aplicacionintermodulargrupo1.Room.ViewModel.RoomViewModel


@Composable
fun GalleryDetailScreen(
    navController: NavController,
    roomViewModel: RoomViewModel
) {
    val selectedRoom by roomViewModel.selectedRoom.collectAsState()
    val selectedIndex by roomViewModel.selectedImageIndex.collectAsState()

    if (selectedRoom == null) {
        Text("No hay imágenes disponibles", color = Color.Red)
        return
    }
    val imageList = selectedRoom!!.imagenes
    var currentIndex by remember { mutableIntStateOf(selectedIndex) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 20.dp, vertical = 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF278498)
                )
            ) {
                Text(
                    text = "Volver",
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            AsyncImage(
                model = imageList[selectedIndex],
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Fit
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(imageList) { index, imageUrl ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { roomViewModel.setSelectedImageIndex(index) } // 🔥 Cambia la imagen al hacer clic
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Miniatura",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}