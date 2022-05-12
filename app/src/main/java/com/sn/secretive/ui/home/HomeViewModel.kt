package com.sn.secretive.ui.home

import androidx.lifecycle.*
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {

    private val _deletePassLiveData: MutableLiveData<Int> = MutableLiveData()
    val deletePassLiveData: LiveData<Int> get() = _deletePassLiveData

    val passwordLiveData: LiveData<MutableList<PasswordItemModel>> =
        passwordRepository.passwords.asLiveData()


    fun deletePassword(passwordItemModel: PasswordItemModel, position: Int) =
        viewModelScope.launch {
            passwordRepository.delete(passwordItemModel)
        }.invokeOnCompletion { err ->
            if (err == null) _deletePassLiveData.value = position
        }

}