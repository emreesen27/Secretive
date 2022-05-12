package com.sn.secretive.data.room

import androidx.room.*
import com.sn.secretive.data.model.PasswordItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password_table")
    fun getPassword(): Flow<MutableList<PasswordItemModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(passwordItemModel: PasswordItemModel)

    @Delete()
    suspend fun delete(passwordItemModel: PasswordItemModel)

}