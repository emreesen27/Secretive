package com.sn.secretive.di

import android.content.Context
import androidx.room.Room
import com.sn.secretive.data.room.DatabaseMigration
import com.sn.secretive.data.room.PasswordDao
import com.sn.secretive.data.room.RoomDatabase
import com.sn.secretive.data.room.SessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideSessionDao(roomDatabase: RoomDatabase): SessionDao {
        return roomDatabase.SessionDao()
    }

    @Provides
    fun providePasswordDao(roomDatabase: RoomDatabase): PasswordDao {
        return roomDatabase.PasswordDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            RoomDatabase::class.java,
            "secretive"
        ).addMigrations(DatabaseMigration.getMigration()).allowMainThreadQueries().build()
    }
}
