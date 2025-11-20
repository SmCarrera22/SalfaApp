package com.example.salfaapp.domain.model.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.salfaapp.domain.model.data.entities.VehiculoMovimientoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiculoMovimientoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovimiento(movimiento: VehiculoMovimientoEntity): Long

    // Nos devuelve un Flow para la UI
    @Query("SELECT * FROM vehiculo_movimientos WHERE vehiculoId = :vehiculoId ORDER BY fechaHora DESC")
    fun getMovimientosByVehiculo(vehiculoId: Long): Flow<List<VehiculoMovimientoEntity>>

    @Query("DELETE FROM vehiculo_movimientos WHERE vehiculoId = :vehiculoId")
    suspend fun deleteMovimientosByVehiculo(vehiculoId: Long)
}