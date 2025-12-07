package com.example.salfaapp.network.repository

import com.example.salfaapp.domain.model.data.dao.VehiculoDao
import com.example.salfaapp.network.api.VehiculoApi
import com.example.salfaapp.network.mapper.VehiculoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VehiculoRemoteRepository(
    private val api: VehiculoApi,
    private val vehiculoDao: VehiculoDao
) {

    suspend fun syncFromServer() = withContext(Dispatchers.IO) {
        val dtos = api.getAll()
        dtos.forEach { dto ->
            val entity = VehiculoMapper.dtoToEntity(dto)
            vehiculoDao.insertVehiculo(entity)
        }
    }

    suspend fun createOnServer(entity: com.example.salfaapp.domain.model.data.entities.VehiculoEntity) =
        withContext(Dispatchers.IO) {
            val dto = VehiculoMapper.entityToDto(entity)
            val created = api.create(dto)
            VehiculoMapper.dtoToEntity(created)
        }

    suspend fun updateOnServer(entity: com.example.salfaapp.domain.model.data.entities.VehiculoEntity) =
        withContext(Dispatchers.IO) {
            val dto = VehiculoMapper.entityToDto(entity)
            val updated = api.update(entity.id, dto)
            VehiculoMapper.dtoToEntity(updated)
        }

    suspend fun deleteOnServer(id: Long) = withContext(Dispatchers.IO) {
        api.delete(id)
    }
}