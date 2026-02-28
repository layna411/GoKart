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
                        val kartData = KartData(
                            lat = convertToDouble(it["lat"]),
                            lng = convertToDouble(it["lng"]),
                            speed = convertToInt(it["speed"]),
                            distance = convertToDouble(it["distance"]),
                            temp = convertToDouble(it["temp"]),
                            battery = convertToIntNullable(it["battery"]),
                            gear = convertToInt(it["gear"]),
                            voltage = convertToFloatNullable(it["voltage"]),
                            sessionTime = convertToFloat(it["sessionTime"]),
                            speedHistory = convertToFloatList(it["speedHistory"]),
                            tempHistory = convertToFloatList(it["tempHistory"])
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

    private fun convertToFloatList(value: Any?): List<Float> {
        return when (value) {
            is List<*> -> value.mapNotNull { item ->
                when (item) {
                    is Number -> item.toFloat()
                    is String -> item.toFloatOrNull()
                    else -> null
                }
            }
            else -> emptyList()
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