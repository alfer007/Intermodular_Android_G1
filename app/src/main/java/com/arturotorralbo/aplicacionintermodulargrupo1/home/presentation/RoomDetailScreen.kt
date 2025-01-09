package com.arturotorralbo.aplicacionintermodulargrupo1.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arturotorralbo.aplicacionintermodulargrupo1.R

@Composable
fun RoomDetailScreen(function: () -> Unit) {

    val galleryImages = listOf(
        R.drawable.pexelscocina,
        R.drawable.pexelsdormitorio,
        R.drawable.pexelshabitacion,
        R.drawable.pexelssalon,
        R.drawable.pexelsmueble
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pexelscomedor),
                contentDescription = "Home Header Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Suit Presidencial",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF278498),
                fontWeight = FontWeight.Bold
            )
        )
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location")
            Text(
                text = "Fill your information below to create an account.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FeatureTag(text = "4 bed")
            FeatureTag(text = "2 bath")
            FeatureTag(text = "320 sq. m.")
        }
        Text(
            text = "Las etiquetas estarán separadas entre sí por 10.dp y seguirán distribuyéndose uniformemente o según el arreglo horizontal que elijas (Arrangement.SpaceEvenly o cualquiera).",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Gallery",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        GallerySection(imageList = galleryImages)

        Spacer(modifier = Modifier.height(20.dp))

        RentButton(price = "900") {

        }
    }
}

@Composable
fun FeatureTag(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        color = Color.White,
        modifier = Modifier
            .background(color = Color(0xFF278498), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
fun GallerySection(imageList: List<Int>) {

    val visibleImages = 3
    val extraImagesCount = if (imageList.size > visibleImages) imageList.size - visibleImages else 0

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {

        items(imageList.take(visibleImages - 1)) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Gallery Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
        }

        if (imageList.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageList[visibleImages - 1]),
                        contentDescription = "Gallery Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (extraImagesCount > 0) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Gray.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$extraImagesCount",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RentButton(price: String, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF278498)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(56.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "Rent for $price/month",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}