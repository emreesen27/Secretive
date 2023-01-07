package com.sn.secretive.data.room

import androidx.room.* // ktlint-disable no-wildcard-imports other-rule-id
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

    @Update()
    suspend fun update(passwordItemModel: PasswordItemModel)
}
