package com.sn.secretive.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sn.secretive.data.model.SessionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM user_table")
    fun getSession(): Flow<SessionModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sessionModel: SessionModel)

    @Query("UPDATE user_table SET pin=:pin")
    suspend fun updatePin(pin: String)
}
