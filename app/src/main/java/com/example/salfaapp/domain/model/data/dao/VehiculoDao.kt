package com.example.salfaapp.domain.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiculoDao {

    @Query("SELECT * FROM vehiculos ORDER BY id DESC")
    fun getAllVehiculos(): Flow<List<VehiculoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehiculo(vehiculo: VehiculoEntity): Long

    @Update
    suspend fun updateVehiculo(vehiculo: VehiculoEntity)

    @Delete
    suspend fun deleteVehiculo(vehiculo: VehiculoEntity)

    @Query("DELETE FROM vehiculos")
    suspend fun deleteAll()
}