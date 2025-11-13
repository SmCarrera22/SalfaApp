package com.example.salfaapp.domain.model.utils

import androidx.room.TypeConverter
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo

class Converters {

    @TypeConverter
    fun fromTipoVehiculo(tipo: TipoVehiculo): String = tipo.name

    @TypeConverter
    fun toTipoVehiculo(value: String): TipoVehiculo = TipoVehiculo.valueOf(value)

    @TypeConverter
    fun fromEstadoVehiculo(estado: EstadoVehiculo): String = estado.name

    @TypeConverter
    fun toEstadoVehiculo(value: String): EstadoVehiculo = EstadoVehiculo.valueOf(value)

    @TypeConverter
    fun fromSucursal(sucursal: Sucursal): String = sucursal.name

    @TypeConverter
    fun toSucursal(value: String): Sucursal = Sucursal.valueOf(value)
}