package com.example.salfaapp.domain.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "talleres")
data class TallerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val rut: Int,
    val codigoVerificador: Int,
    val tipo: String,
    val direccion: String,
    val encargado: String
)