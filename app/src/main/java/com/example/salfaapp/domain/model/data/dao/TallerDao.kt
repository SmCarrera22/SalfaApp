package com.example.salfaapp.domain.model.data.dao

import androidx.room.*
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TallerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaller(taller: TallerEntity)

    @Query("SELECT * FROM talleres ORDER BY nombre ASC")
    fun getAllTalleres(): Flow<List<TallerEntity>>

    @Query("SELECT * FROM talleres WHERE id = :tallerId LIMIT 1")
    suspend fun getTallerById(tallerId: Int): TallerEntity?

    @Update
    suspend fun updateTaller(taller: TallerEntity)

    @Delete
    suspend fun deleteTaller(taller: TallerEntity)
}