package com.abhishek.radiustask.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhishek.radiustask.pojos.Exclusions
import com.abhishek.radiustask.pojos.ExclusionsList
import com.abhishek.radiustask.pojos.Facilities
import com.abhishek.radiustask.util.Converters
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/*
@Database(entities = [FacilitiesData::class,ExclusionData::class], version = 1, exportSchema = false)*/
@Database(entities = [Facilities::class, ExclusionsList::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): AppDao

    companion object {
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "app_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}