package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.data.dao.VehiculoDao
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity

class VehiculoRepository(private val dao: VehiculoDao) {

    fun obtenerVehiculos() = dao.getAllVehiculos()

    suspend fun guardarVehiculo(entity: VehiculoEntity): Long {
        return dao.insertVehiculo(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}