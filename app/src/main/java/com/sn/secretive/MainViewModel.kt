package com.sn.secretive

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sn.secretive.data.repository.SessionRepository
import com.sn.secretive.data.model.SessionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(sessionRepository: SessionRepository) : ViewModel() {
    val sessionLiveData: LiveData<SessionModel> = sessionRepository.session.asLiveData()
}