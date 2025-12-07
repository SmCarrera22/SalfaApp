package com.example.salfaapp.network.mapper

import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.network.dto.VehiculoDto
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo

object VehiculoMapper {

    fun dtoToEntity(dto: VehiculoDto): VehiculoEntity {
        return VehiculoEntity(
            id = dto.id ?: 0L,
            marca = dto.marca,
            modelo = dto.modelo,
            anio = dto.anio,
            tipo = try { TipoVehiculo.valueOf(dto.tipo) } catch (e: Exception) { TipoVehiculo.SEDAN },
            patente = dto.patente,
            estado = try { EstadoVehiculo.valueOf(dto.estado) } catch (e: Exception) { EstadoVehiculo.Nuevo_Ingreso },
            sucursal = try { Sucursal.valueOf(dto.sucursal) } catch (e: Exception) { Sucursal.Autopark },
            tallerAsignado = dto.tallerAsignado,
            observaciones = dto.observaciones
        )
    }

    fun entityToDto(entity: VehiculoEntity): VehiculoDto {
        return VehiculoDto(
            id = if (entity.id <= 0L) null else entity.id,
            marca = entity.marca,
            modelo = entity.modelo,
            anio = entity.anio,
            tipo = entity.tipo.name,
            patente = entity.patente,
            estado = entity.estado.name,
            sucursal = entity.sucursal.name,
            tallerAsignado = entity.tallerAsignado,
            observaciones = entity.observaciones
        )
    }

}