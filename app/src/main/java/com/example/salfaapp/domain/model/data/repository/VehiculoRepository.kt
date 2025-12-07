package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.model.data.dao.VehiculoDao
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import kotlinx.coroutines.flow.Flow

class VehiculoRepository(private val dao: VehiculoDao) {

    fun getAllVehiculos(): Flow<List<VehiculoEntity>> {
        return dao.getAllVehiculos()
    }

    suspend fun guardarVehiculo(entity: VehiculoEntity): Long {
        return dao.insertVehiculo(entity)
    }

    suspend fun eliminarVehiculo(vehiculo: VehiculoEntity) {
        dao.deleteVehiculo(vehiculo)
    }

    suspend fun getVehiculoById(id: Long): VehiculoEntity? {
        return dao.getVehiculoById(id)
    }
}