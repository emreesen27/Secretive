package com.sn.secretive.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.model.SessionModel

@Database(
    entities = [SessionModel::class, PasswordItemModel::class],
    version = 2,
    exportSchema = false,
)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun SessionDao(): SessionDao
    abstract fun PasswordDao(): PasswordDao
}
