package com.example.rxjava4.di

import android.content.Context
import com.example.rxjava4.Constants
import com.example.rxjava4.di.DatabaseInfo
import com.example.rxjava4.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @DatabaseInfo
    fun providerDatabaseName(): String {
        return Constants.Table.DB_NAME
    }
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context, @DatabaseInfo dbName: String) = AppDatabase.getDatabase(appContext, dbName)

    @Singleton
    @Provides
    fun provideNoteDao(db: AppDatabase) = db.noteDao()

}