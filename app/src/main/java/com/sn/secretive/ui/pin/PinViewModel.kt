package com.sn.secretive.ui.pin

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports other-rule-id
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

    fun insert(sessionModel: SessionModel) = viewModelScope.launch {
        sessionRepository.insert(sessionModel)
    }
}
