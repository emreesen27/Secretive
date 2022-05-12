package com.sn.secretive.data.repository

import androidx.annotation.WorkerThread
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.room.PasswordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasswordRepository @Inject constructor(private val passwordDao: PasswordDao) {

    val passwords: Flow<MutableList<PasswordItemModel>> = passwordDao.getPassword()

    @WorkerThread
    suspend fun insert(passwordItemModel: PasswordItemModel) {
        return passwordDao.insert(passwordItemModel)
    }

    @WorkerThread
    suspend fun delete(passwordItemModel: PasswordItemModel) {
        return passwordDao.delete(passwordItemModel)
    }

}