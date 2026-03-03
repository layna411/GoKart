package com.simats.gokart.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.simats.gokart.data.TuningProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DigitalTuneViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(TuningProfile())
    val uiState: StateFlow<TuningProfile> = _uiState.asStateFlow()

    private val vehicleType: String = savedStateHandle.get<String>("vehicleType") ?: "ev"
    private val dbRef = FirebaseDatabase.getInstance().getReference("gokarts/$vehicleType/tuning")

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = snapshot.value as? Map<String, Any>
                    map?.let {
                        val profile = TuningProfile(
                            maxSpeed = convertToInt(it["maxSpeed"]),
                            acceleration = convertToInt(it["acceleration"]),
                            regenerativeBraking = convertToInt(it["regenerativeBraking"])
                        )
                        _uiState.value = profile
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    fun updateTuning(profile: Map<String, Any>) {
        dbRef.setValue(profile)
    }

    private fun convertToInt(value: Any?): Int {
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull() ?: 100
            else -> 100
        }
    }
}