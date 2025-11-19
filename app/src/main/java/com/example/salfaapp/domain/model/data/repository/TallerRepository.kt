package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.data.dao.TallerDao
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import kotlinx.coroutines.flow.Flow

class TallerRepository(private val dao: TallerDao) {

    fun getAllTalleres(): Flow<List<TallerEntity>> {
        return dao.getAllTalleres()
    }

    suspend fun agregarTaller(taller: TallerEntity) {
        dao.insertTaller(taller)
    }

    suspend fun eliminarTaller(taller: TallerEntity) {
        dao.deleteTaller(taller)
    }
}