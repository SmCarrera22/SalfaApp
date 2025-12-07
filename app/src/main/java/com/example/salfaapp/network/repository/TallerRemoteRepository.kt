package com.example.salfaapp.network.repository

import com.example.salfaapp.domain.model.data.dao.TallerDao
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.network.api.TallerApi
import com.example.salfaapp.network.mapper.TallerMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TallerRemoteRepository(
    private val api: TallerApi,
    private val tallerDao: TallerDao
) {

    suspend fun syncFromServer() = withContext(Dispatchers.IO) {
        val dtos = api.getAll()
        dtos.forEach { dto ->
            val entity = TallerMapper.dtoToEntity(dto)
            tallerDao.insertTaller(entity)
        }
    }

    suspend fun createOnServer(entity: com.example.salfaapp.domain.model.data.entities.TallerEntity) =
        withContext(Dispatchers.IO) {
            val dto = TallerMapper.entityToDto(entity)
            val created = api.create(dto)
            TallerMapper.dtoToEntity(created)
        }

    // Obtener solo un taller remoto
    suspend fun getTallerById(id: Int): TallerEntity? = withContext(Dispatchers.IO) {
        return@withContext try {
            val dto = api.getById(id)
            TallerMapper.dtoToEntity(dto)
        } catch (e: Exception) {
            null
        }
    }

    // Crear en servidor
    suspend fun insert(entity: TallerEntity) = withContext(Dispatchers.IO) {
        val dto = TallerMapper.entityToDto(entity)
        val created = api.create(dto)
        TallerMapper.dtoToEntity(created)
    }

    // Actualizar en servidor
    suspend fun update(entity: TallerEntity) = withContext(Dispatchers.IO) {
        val dto = TallerMapper.entityToDto(entity)
        val updated = api.update(entity.id, dto)
        TallerMapper.dtoToEntity(updated)
    }

    // Eliminar en servidor
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        api.delete(id)
    }
}