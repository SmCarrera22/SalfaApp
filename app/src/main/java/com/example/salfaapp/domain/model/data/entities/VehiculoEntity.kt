package com.example.salfaapp.domain.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo

@Entity(tableName = "vehiculos")
data class VehiculoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val marca: String,
    val modelo: String,
    val anio: Int?,
    val tipo: TipoVehiculo,
    val patente: String,
    val estado: EstadoVehiculo,
    val sucursal: Sucursal,
    val tallerAsignado: String?,
    val observaciones: String?
)