package com.eventplanner.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

@Composable
fun NetworkIcon(
    modifier: Modifier = Modifier,
    url: String,
    ) {
    Box(modifier = modifier.size(20.dp)) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    size(Size.ORIGINAL)
                    scale(scale = Scale.FIT)
                    error(com.eventplanner.R.drawable.ic_weather_sunny)
                }).build()
        )

        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
    }
}