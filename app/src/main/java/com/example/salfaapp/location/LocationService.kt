package com.example.salfaapp.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

class LocationService(private val context: Context) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(onResult: (Double, Double) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                onResult(location.latitude, location.longitude)
            }
        }
    }
}