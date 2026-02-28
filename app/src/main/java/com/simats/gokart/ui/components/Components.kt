package com.simats.gokart.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.gokart.ui.theme.AccentAmber
import com.simats.gokart.ui.theme.BgCharcoal
import com.simats.gokart.ui.theme.BgPanel
import com.simats.gokart.ui.theme.BorderColor
import com.simats.gokart.ui.theme.StatusGreen
import com.simats.gokart.ui.theme.StatusRed
import com.simats.gokart.ui.theme.TextPrimary
import com.simats.gokart.ui.theme.TextSecondary

@Composable
fun MetricCard(
    icon: ImageVector,
    label: String,
    value: String,
    unit: String,
    accentColor: Color = AccentAmber,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(BgPanel, RoundedCornerShape(12.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = BorderColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = label.uppercase(),
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    color = accentColor,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = unit,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun HeaderBar(sessionTime: Float) {
    val infiniteTransition = rememberInfiniteTransition(label = "Blink")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BlinkAlpha"
    )

    fun formatTime(seconds: Float): String {
        val s = seconds.toInt()
        val h = s / 3600
        val m = (s % 3600) / 60
        val sec = s % 60
        return "%02d:%02d:%02d".format(h, m, sec)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(BgPanel)
            .border(width = 0.dp, color = Color.Transparent) // Remove potential double border issues
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = "KART-07",
                    color = AccentAmber,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "EV TELEMETRY",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(BorderColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(BgCharcoal, RoundedCornerShape(4.dp))
                    .border(1.dp, BorderColor, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "SESSION ID: 8F2-A9",
                    color = TextSecondary,
                    fontSize = 10.sp
                )
            }
        }

        // Center: Time
        Text(
            text = formatTime(sessionTime),
            color = TextPrimary,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            //modifier = Modifier.align(Alignment.CenterVertically) // Not needed in Row scope if alignment is set on Row
        )

        // Right: Status
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(StatusGreen, CircleShape)
                        //.shadow(4.dp, StatusGreen,CircleShape)
                )
                Text(
                    text = "CONNECTED",
                    color = StatusGreen,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = null,
                    tint = StatusGreen,
                    modifier = Modifier.size(12.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.alpha(alpha)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(StatusRed, CircleShape)
                        //.shadow(4.dp, StatusRed)
                )
                Text(
                    text = "REC",
                    color = StatusRed,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
                Icon(
                    imageVector = Icons.Default.MonitorHeart,
                    contentDescription = null,
                    tint = StatusRed,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}
