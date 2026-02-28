package com.simats.gokart.data

data class LapTime(
    val lap: Int,
    val time: String, // MM:SS.ms
    val s1: String,
    val s2: String,
    val s3: String
)

enum class SystemStatusState {
    NOMINAL, CAUTION, CRITICAL
}

data class SystemStatus(
    val motor: SystemStatusState = SystemStatusState.NOMINAL,
    val battery: SystemStatusState = SystemStatusState.NOMINAL,
    val comms: SystemStatusState = SystemStatusState.NOMINAL,
    val tires: SystemStatusState = SystemStatusState.NOMINAL
)

data class DataPoint(val time: Long, val value: Float)

data class TelemetryData(
    val speed: Int = 0,
    val speedHistory: List<DataPoint> = listOf(
        5f, 10f, 12f, 18f, 20f, 22f, 23f, 23f, 20f, 15f, 10f, 8f, 12f, 15f, 25f, 30f, 32f, 33f, 35f, 40f,
        41f, 40f, 38f, 35f, 30f, 25f, 28f, 35f, 38f, 45f, 50f, 51f, 52f, 50f, 48f, 45f, 40f, 38f, 35f, 30f
    ).mapIndexed { index, value -> DataPoint(index.toLong(), value) },
    val battery: Float = 98f,
    val voltage: Float = 48.2f,
    val temperature: Float = 45f,
    val tempHistory: List<DataPoint> = List(30) { DataPoint(it.toLong(), 45f) },
    val distance: Int = 1240,
    val currentLap: Int = 3,
    val lapTimes: List<LapTime> = listOf(
        LapTime(1, "01:12.450", "24.1", "23.8", "24.5"),
        LapTime(2, "01:11.890", "23.9", "23.5", "24.4")
    ),
    val systemStatus: SystemStatus = SystemStatus(),
    val sessionTime: Float = 0f,
    val isRecording: Boolean = true,
    val gear: Int = 1
)