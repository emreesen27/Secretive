package com.sn.secretive

import androidx.lifecycle.*
import com.sn.secretive.data.SessionRepository
import com.sn.secretive.data.model.SessionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

    fun insert(sessionModel: SessionModel) = viewModelScope.launch {
        sessionRepository.insert(sessionModel)
    }

}