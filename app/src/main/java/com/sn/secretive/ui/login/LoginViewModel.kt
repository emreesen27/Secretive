package com.sn.secretive.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(sessionRepository: SessionRepository) :
    ViewModel() {

    val session: LiveData<SessionModel> = sessionRepository.session.asLiveData()

    fun checkPIN(pin: String): Int {
        val tPin = session.value?.pin ?: return DB_ERR
        return if (tPin == pin) SUCCESS else WRONG_PIN
    }

    companion object {
        const val SUCCESS = 200
        const val WRONG_PIN = 403
        const val DB_ERR = 404
    }
}
