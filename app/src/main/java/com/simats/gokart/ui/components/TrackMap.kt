package com.simats.gokart.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.simats.gokart.viewmodels.TrackViewModel

@Composable
fun TrackMap(
    isFullScreen: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val viewModel: TrackViewModel = viewModel()
    val vehicleLocation by viewModel.vehicleLocation.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(vehicleLocation, 16f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .let { if (onClick != null && !isFullScreen) it.clickable(onClick = onClick) else it }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = vehicleLocation),
                title = "GoKart"
            )
        }
    }
}