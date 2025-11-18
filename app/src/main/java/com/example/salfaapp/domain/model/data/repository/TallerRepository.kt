package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.data.dao.TallerDao
import com.example.salfaapp.domain.model.data.entities.TallerEntity

class TallerRepository(private val dao: TallerDao) {

    val talleres = dao.getAllTalleres()

    suspend fun agregarTaller(taller: TallerEntity) {
        dao.insertTaller(taller)
    }

    suspend fun eliminarTaller(taller: TallerEntity) {
        dao.deleteTaller(taller)
    }
}