package com.example.salfaapp.domain.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.salfaapp.domain.model.EstadoVehiculo

@Entity(tableName = "vehiculo_movimientos")
data class VehiculoMovimientoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val vehiculoId: Long,
    val estadoAnterior: EstadoVehiculo,
    val estadoNuevo: EstadoVehiculo,
    val fechaHora: Long // lo guardamos como timestamp
)