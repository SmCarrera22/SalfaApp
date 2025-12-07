package com.example.salfaapp.network.mapper

import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.network.dto.TallerDto

object TallerMapper {
    fun dtoToEntity(dto: TallerDto): TallerEntity {
        return TallerEntity(
            id = dto.id ?: 0,
            nombre = dto.nombre,
            rut = dto.rut,
            codigoVerificador = dto.codigoVerificador,
            tipo = dto.tipo,
            direccion = dto.direccion,
            encargado = dto.encargado
        )
    }

    fun entityToDto(entity: TallerEntity): TallerDto {
        return TallerDto(
            id = if (entity.id <= 0) null else entity.id,
            nombre = entity.nombre,
            rut = entity.rut,
            codigoVerificador = entity.codigoVerificador,
            tipo = entity.tipo,
            direccion = entity.direccion,
            encargado = entity.encargado
        )
    }
}