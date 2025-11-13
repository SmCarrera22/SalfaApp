package com.example.salfaapp.domain.model.data.repository

import com.example.salfaapp.domain.model.data.dao.FormularioServicioDao
import com.example.salfaapp.domain.model.data.entities.FormularioServicioEntity

class FormularioServicioRepository(private val dao: FormularioServicioDao) {

    fun obtenerFormularios() = dao.getFormularios()

    suspend fun guardarFormulario(entity: FormularioServicioEntity): Long {
        return dao.insertFormulario(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}