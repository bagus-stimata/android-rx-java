package com.example.rxjava4.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rxjava4.local.dao.NoteDao
import com.example.rxjava4.local.entity.NoteEntity
import com.example.rxjava4.local.entity.RoomDateConverters

@Database(entities = [NoteEntity::class], version = 3, exportSchema = false)
@TypeConverters(RoomDateConverters::class) //Karena ada Native Date
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context, dbName: String): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context, dbName).also { instance = it } }

        private fun buildDatabase(appContext: Context, dbName: String) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
                .build()
    }

}