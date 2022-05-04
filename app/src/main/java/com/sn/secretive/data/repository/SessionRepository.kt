package com.sn.secretive.data.repository

import androidx.annotation.WorkerThread
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.data.room.SessionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepository @Inject constructor(private val sessionDao: SessionDao) {

    val session: Flow<SessionModel> = sessionDao.getSession()

    @WorkerThread
    suspend fun insert(sessionModel: SessionModel) {
        return sessionDao.insert(sessionModel)
    }

}