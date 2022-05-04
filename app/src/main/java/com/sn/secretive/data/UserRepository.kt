package com.sn.secretive.data

import androidx.annotation.WorkerThread
import com.sn.secretive.data.model.UserModel
import com.sn.secretive.data.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    val alFoodItems: Flow<UserModel> = userDao.getUser()

    @WorkerThread
    suspend fun insert(userModel: UserModel) {
        return userDao.insert(userModel)
    }

}