package com.simats.gokart.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrackViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val vehicleType: String = savedStateHandle.get<String>("vehicleType") ?: "ev"
    private val dbRef = FirebaseDatabase.getInstance().getReference("gokarts/$vehicleType")

    private val _vehicleLocation = MutableStateFlow(LatLng(9.9234, 78.1198))
    val vehicleLocation: StateFlow<LatLng> = _vehicleLocation

    init {
        fetchVehicleLocation()
    }

    private fun fetchVehicleLocation() {
        viewModelScope.launch {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val latObj = snapshot.child("lat").value
                    val lngObj = snapshot.child("lng").value

                    val lat = (latObj as? Number)?.toDouble() ?: latObj.toString().toDoubleOrNull()
                    val lng = (lngObj as? Number)?.toDouble() ?: lngObj.toString().toDoubleOrNull()

                    if (lat != null && lng != null) {
                        _vehicleLocation.value = LatLng(lat, lng)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}