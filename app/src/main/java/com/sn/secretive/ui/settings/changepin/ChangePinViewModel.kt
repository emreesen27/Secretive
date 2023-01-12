package com.sn.secretive.ui.settings.changepin

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePinViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) :
    ViewModel() {

    private val _updatePinLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val updatePinLiveData: LiveData<Boolean> get() = _updatePinLiveData

    private val session: Flow<SessionModel> = sessionRepository.session
    private var pin: String? = null
    val btnEnabled = ObservableBoolean(false)

    fun onInfoChange(oldPin: String, pin: String, pinAgain: String) {
        btnEnabled.set(oldPin.isNotEmpty() && pin.isNotEmpty() && pinAgain.isNotEmpty())
    }

    init {
        getPin()
    }

    private fun getPin() {
        viewModelScope.launch {
            session.collect { s ->
                pin = s.pin
            }
        }
    }

    fun controlPin(oldPin: String) = oldPin == pin

    fun pinsIsEquals(pin: String, pinAgain: String): Boolean = pin == pinAgain

    fun updatePin(pin: String) = viewModelScope.launch {
        sessionRepository.updatePin(pin)
    }.invokeOnCompletion { err ->
        if (err == null) _updatePinLiveData.value = true
    }
}
