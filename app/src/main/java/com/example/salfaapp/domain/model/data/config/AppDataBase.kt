package com.example.salfaapp.domain.model.data.config

import android.content.Context
import androidx.room.*
import com.example.salfaapp.domain.model.data.dao.VehiculoDao
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.utils.Converters

@Database(
    entities = [VehiculoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vehiculoDao(): VehiculoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "salfa_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}