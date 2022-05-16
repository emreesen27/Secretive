package com.sn.secretive.ui.password.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {

    private val _updateLiveData: MutableLiveData<PasswordItemModel> = MutableLiveData()
    val updateLiveData: LiveData<PasswordItemModel> get() = _updateLiveData

    fun update(passwordItemModel: PasswordItemModel) = viewModelScope.launch {
        passwordRepository.update(passwordItemModel)
    }.invokeOnCompletion { err ->
        if (err == null) _updateLiveData.value = passwordItemModel
    }

    companion object {
        const val TITLE = "Title"
        const val PASSWORD = "Password"
        const val NOTE = "Note"
    }

}