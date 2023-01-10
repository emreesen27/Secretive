package com.sn.secretive.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "password_table")
data class PasswordItemModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "note")
    val note: String?,
    @ColumnInfo(name = "icon_name")
    val iconName: String,
    @ColumnInfo(name = "last_edit_date")
    val lastEditDate: String?
) : Parcelable
