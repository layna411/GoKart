package com.simats.gokart.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlin.math.ceil

@Composable
fun BatteryPanel(level: Float, voltage: Float) {
    val segments = 10
    val activeSegments = ceil(level / 10).toInt()

    fun getSegmentColor(index: Int): Color {
        // Index 0 is bottom
        return when {
            level <= 20 -> StatusRed
            level <= 40 -> AccentAmber
            else -> StatusGreen
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth() // Assuming grid cell context, but fillMaxWidth for flexibility within parent
            .height(140.dp) // Adjust height as needed to match design ratio
            .background(BgPanel)
            .padding(16.dp)
    )    {
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BATTERY",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = if (level < 20) StatusRed else AccentAmber,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "%.1f".format(voltage),
                        color = TextPrimary,
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "V",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                    )
                }
                Text(
                    text = "${level.toInt()}% CHARGE",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(80.dp)
                    .background(BgCharcoal)
                    .border(1.dp, BorderColor)
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Bottom)
                ) {
                    repeat(segments) { i ->
                        val segmentIndex = 9 - i
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(
                                    if (segmentIndex < activeSegments) getSegmentColor(segmentIndex) else BgCharcoal.copy(alpha = 0.2f)
                                )
                        )
                    }
                }
            }
        }
    }
}
