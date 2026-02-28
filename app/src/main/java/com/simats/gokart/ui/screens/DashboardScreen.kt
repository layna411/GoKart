package com.simats.gokart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.gokart.data.DataPoint
import com.simats.gokart.ui.components.BatteryPanel
import com.simats.gokart.ui.components.MetricCard
import com.simats.gokart.ui.components.MetricDetailModal
import com.simats.gokart.ui.components.SimpleTopAppBar
import com.simats.gokart.ui.components.TrackMap
import com.simats.gokart.ui.theme.AccentAmber
import com.simats.gokart.ui.theme.BgCharcoal
import com.simats.gokart.ui.theme.BgPanel
import com.simats.gokart.ui.theme.BorderColor
import com.simats.gokart.ui.theme.StatusGreen
import com.simats.gokart.ui.theme.StatusRed
import com.simats.gokart.viewmodel.TelemetryViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: TelemetryViewModel = viewModel(),
    vehicleType: String
) {
    val data by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var selectedMetric by remember { mutableStateOf<String?>(null) }

    fun formatTime(seconds: Float): String {
        val s = seconds.toInt()
        val m = s / 60
        val sec = s % 60
        return "%d:%02d".format(m, sec)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCharcoal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            SimpleTopAppBar()

            // 3D Viewer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                KartScreen(vehicleType = vehicleType)
            }

            // Metrics Grid
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Default.Speed,
                            label = "Speed",
                            value = data.speed.toString(),
                            unit = "KM/H",
                            onClick = { selectedMetric = "speed" }
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Default.LocationOn,
                            label = "Distance",
                            value = "%.2f".format(data.distance / 1000),
                            unit = "KM",
                            accentColor = Color(0xFF3b82f6),
                            onClick = { selectedMetric = "distance" }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Default.Timer,
                            label = "Time",
                            value = formatTime(data.sessionTime),
                            unit = "MIN",
                            accentColor = StatusGreen,
                            onClick = { selectedMetric = "time" }
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Default.Thermostat,
                            label = "Temp",
                            value = "%.1f".format(data.temp),
                            unit = "°C",
                            accentColor = StatusRed,
                            onClick = { selectedMetric = "temp" }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (vehicleType == "ev") {
                    // Battery Panel
                    data.battery?.let {
                        Box(modifier = Modifier.clickable { selectedMetric = "battery" }) {
                            BatteryPanel(
                                level = it.toFloat(),
                                voltage = data.voltage ?: 0f
                            )
                        }
                    }
                } else {
                    // Gear Panel
                    MetricCard(
                        icon = Icons.Default.Speed, // Replace with a more appropriate icon
                        label = "Gear",
                        value = data.gear.toString(),
                        unit = "",
                        onClick = { selectedMetric = "gear" }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Track Map
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BgPanel, RoundedCornerShape(12.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    TrackMap(
                        onClick = { navController.navigate("track/$vehicleType") }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Modal
        if (selectedMetric != null) {
            val metricData = when (selectedMetric) {
                "speed" -> Triple(data.speed.toString(), "KM/H", "Real-time velocity from rear axle sensor.")
                "distance" -> Triple("%.2f".format(data.distance / 1000), "KM", "Total distance covered in current session.")
                "time" -> Triple(formatTime(data.sessionTime), "MIN", "Elapsed time since ignition start.")
                "temp" -> Triple("%.1f".format(data.temp), "°C", "Core temperature of the electric motor.")
                "battery" -> Triple(
                    data.battery?.let { "%.0f".format(it.toFloat()) } ?: "N/A",
                    "%",
                    "Battery state of charge at ${data.voltage?.let { "%.1fV".format(it) } ?: "N/A"}."
                )
                "gear" -> Triple(data.gear.toString(), "", "Current gear.")
                else -> Triple("", "", "")
            }

            val history = when (selectedMetric) {
                "speed" -> data.speedHistory.mapIndexed { index, value -> DataPoint(index.toLong(), value) }
                "temp" -> data.tempHistory.mapIndexed { index, value -> DataPoint(index.toLong(), value) }
                else -> emptyList()
            }

            MetricDetailModal(
                isOpen = true,
                onClose = { selectedMetric = null },
                title = selectedMetric ?: "",
                value = metricData.first,
                unit = metricData.second,
                description = metricData.third,
                history = history,
                color = when (selectedMetric) {
                    "distance" -> Color(0xFF3b82f6)
                    "time" -> StatusGreen
                    "temp" -> StatusRed
                    else -> AccentAmber
                }
            )
        }
    }
}
