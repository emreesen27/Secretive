package com.sn.secretive.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigration {

    fun getMigration() = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE password_table ADD COLUMN last_edit_date TEXT")
        }
    }

}