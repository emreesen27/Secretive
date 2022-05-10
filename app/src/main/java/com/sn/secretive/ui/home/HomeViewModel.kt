package com.sn.secretive.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(passwordRepository: PasswordRepository) :
    ViewModel() {

    val passwordLiveData: LiveData<MutableList<PasswordItemModel>> =
        passwordRepository.passwords.asLiveData()


}