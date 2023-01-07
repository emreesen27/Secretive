package com.sn.secretive.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class SessionModel(
    @PrimaryKey
    @ColumnInfo(name = "pin")
    val pin: String,
    @ColumnInfo(name = "last_login")
    val lastLogin: String
)
