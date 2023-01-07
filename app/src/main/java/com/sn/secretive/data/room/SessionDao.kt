package com.sn.secretive.data.room

import androidx.room.* // ktlint-disable no-wildcard-imports other-rule-id
import com.sn.secretive.data.model.SessionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM user_table")
    fun getSession(): Flow<SessionModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sessionModel: SessionModel)
}
