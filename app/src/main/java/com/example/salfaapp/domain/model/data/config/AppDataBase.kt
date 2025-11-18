package com.example.salfaapp.domain.model.data.config

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.salfaapp.domain.model.data.dao.VehiculoDao
import com.example.salfaapp.domain.model.data.dao.VehiculoMovimientoDao
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.data.entities.VehiculoMovimientoEntity
import com.example.salfaapp.domain.model.utils.Converters

@Database(
    entities = [
        VehiculoEntity::class,
        VehiculoMovimientoEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vehiculoDao(): VehiculoDao
    abstract fun movimientoDao(): VehiculoMovimientoDao

    companion object {

        // ============================
        // MIGRACIÓN 1 → 2
        // ============================
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS vehiculo_movimientos (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        vehiculoId INTEGER NOT NULL,
                        estadoAnterior TEXT NOT NULL,
                        estadoNuevo TEXT NOT NULL,
                        fechaHora INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "salfa_db"
                )
                    .addMigrations(MIGRATION_1_2) // ← AHORA NO BORRA NADA
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}