package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.data.dao.TallerDao
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import kotlinx.coroutines.flow.Flow

class TallerRepository(private val dao: TallerDao) {

    fun getAllTalleres(): Flow<List<TallerEntity>> = dao.getAllTalleres()

    suspend fun getTallerById(id: Int): TallerEntity? =
        dao.getTallerById(id)

    suspend fun insert(taller: TallerEntity) =
        dao.insertTaller(taller)

    suspend fun update(taller: TallerEntity) =
        dao.updateTaller(taller)

    suspend fun delete(taller: TallerEntity) =
        dao.deleteTaller(taller)
}