package com.simats.gokart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.gokart.ui.theme.GokartTheme

@Composable
fun DetailsScreen(vehicleType: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Technical Specs", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Hardware configuration and real-time system status.", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Text("HARDWARE CONFIG", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (vehicleType == "ev") {
                    HardwareConfigRow(icon = Icons.Default.Adjust, title = "Chassis", value = "Custom made chassis")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.FlashOn, title = "Motor", value = "Motor 4KW 48V")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.Memory, title = "Controller", value = "SPPMS DCBC")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.BatteryFull, title = "Battery", value = "42v lithium ion")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.Wifi, title = "Telemetry", value = "ESP32 + Firebase")
                } else {
                    HardwareConfigRow(icon = Icons.Default.Adjust, title = "Chassis", value = "Custom made chassis")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.Build, title = "Engine", value = "FZ 149cc")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.Sync, title = "Transmission", value = "Direct drive")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.LocalGasStation, title = "Fuel System", value = "Petrol")
                    Divider()
                    HardwareConfigRow(icon = Icons.Default.Wifi, title = "Telemetry", value = "ESP32 + Firebase")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("SYSTEM HEALTH", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (vehicleType == "ev") {
                SystemHealthCard(title = "MOTOR", status = "NOMINAL", modifier = Modifier.weight(1f))
                SystemHealthCard(title = "BATTERY", status = "NOMINAL", modifier = Modifier.weight(1f))
            } else {
                SystemHealthCard(title = "ENGINE", status = "NOMINAL", modifier = Modifier.weight(1f))
                SystemHealthCard(title = "FUEL", status = "NOMINAL", modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SystemHealthCard(title = "COMMS", status = "NOMINAL", modifier = Modifier.weight(1f))
            SystemHealthCard(title = "TIRES", status = "NOMINAL", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text("GOKART", fontSize = 12.sp, color = Color.Gray)
            Text("LAST SYNC: JUST NOW", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun HardwareConfigRow(icon: ImageVector, title: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(value, fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun SystemHealthCard(title: String, status: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(Color.Green, shape = RoundedCornerShape(4.dp)))
                Spacer(modifier = Modifier.width(8.dp))
                Text(status, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Green)
            }
        }
    }
}

@Preview(showBackground = true, name = "EV Details")
@Composable
fun DetailsScreenPreview() {
    GokartTheme {
        DetailsScreen("ev")
    }
}

@Preview(showBackground = true, name = "Petrol Details")
@Composable
fun PetrolDetailsScreenPreview() {
    GokartTheme {
        DetailsScreen("petrol")
    }
}
