package com.simats.gokart.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class KartData(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val speed: Int = 0,
    val distance: Double = 0.0,
    val temp: Double = 0.0,
    val battery: Int? = null,
    val gear: Int = 0,
    val voltage: Float? = null,
    val sessionTime: Float = 0.0f,
    val speedHistory: List<Float> = emptyList(),
    val tempHistory: List<Float> = emptyList()
)
