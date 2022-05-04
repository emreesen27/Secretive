package com.sn.secretive.data.room

import androidx.room.*
import com.sn.secretive.data.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getUser(): Flow<UserModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userModel: UserModel)


}