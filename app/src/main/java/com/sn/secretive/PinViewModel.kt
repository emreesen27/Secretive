package com.sn.secretive

import androidx.lifecycle.*
import com.sn.secretive.data.UserRepository
import com.sn.secretive.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun insert(userModel: UserModel) = viewModelScope.launch {
        userRepository.insert(userModel)
    }

}