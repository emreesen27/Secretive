package com.sn.secretive.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sn.secretive.data.model.SessionModel

@Database(entities = [SessionModel::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun SessionDao(): SessionDao
}