package com.simats.gokart.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.simats.gokart.data.KartData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TelemetryViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(KartData())
    val uiState: StateFlow<KartData> = _uiState.asStateFlow()

    private val vehicleType: String = savedStateHandle.get<String>("vehicleType") ?: "ev"
    
    // Local buffers for live trend
    private val speedHistory = mutableListOf<Float>()
    private val tempHistory = mutableListOf<Float>()
    private val maxHistoryPoints = 50

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val dbRef = FirebaseDatabase.getInstance().getReference("gokarts/$vehicleType")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = snapshot.value as? Map<String, Any>
                    map?.let {
                        val currentSpeed = convertToFloat(it["speed"])
                        val currentTemp = convertToFloat(it["temp"])

                        // Update local history buffers
                        speedHistory.add(currentSpeed)
                        if (speedHistory.size > maxHistoryPoints) speedHistory.removeAt(0)
                        
                        tempHistory.add(currentTemp)
                        if (tempHistory.size > maxHistoryPoints) tempHistory.removeAt(0)

                        val kartData = KartData(
                            lat = convertToDouble(it["lat"]),
                            lng = convertToDouble(it["lng"]),
                            speed = currentSpeed.toInt(),
                            distance = convertToDouble(it["distance"]),
                            temp = currentTemp.toDouble(),
                            battery = convertToIntNullable(it["battery"]),
                            gear = convertToInt(it["gear"]),
                            voltage = convertToFloatNullable(it["voltage"]),
                            sessionTime = convertToFloat(it["sessionTime"]),
                            // Use local buffers if Firebase doesn't provide history
                            speedHistory = speedHistory.toList(),
                            tempHistory = tempHistory.toList()
                        )
                        _uiState.value = kartData
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    private fun convertToDouble(value: Any?): Double {
        return when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull() ?: 0.0
            else -> 0.0
        }
    }

    private fun convertToInt(value: Any?): Int {
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull() ?: 0
            else -> 0
        }
    }

    private fun convertToFloat(value: Any?): Float {
        return when (value) {
            is Number -> value.toFloat()
            is String -> value.toFloatOrNull() ?: 0.0f
            else -> 0.0f
        }
    }

    private fun convertToIntNullable(value: Any?): Int? {
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            else -> null
        }
    }

    private fun convertToFloatNullable(value: Any?): Float? {
        return when (value) {
            is Number -> value.toFloat()
            is String -> value.toFloatOrNull()
            else -> null
        }
    }
}
