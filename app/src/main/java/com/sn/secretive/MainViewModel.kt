package com.sn.secretive

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sn.secretive.data.UserRepository
import com.sn.secretive.data.model.UserModel
import com.sn.secretive.data.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    val userLiveData: LiveData<UserModel> = userRepository.alFoodItems.asLiveData()
}