package com.sn.secretive.ui.password.add


import androidx.databinding.ObservableBoolean
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
class AddPasswordViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {

    private val _insertLiveData: MutableLiveData<PasswordItemModel> = MutableLiveData()
    val insertLiveData: LiveData<PasswordItemModel> get() = _insertLiveData

    val btnSaveEnabled = ObservableBoolean(false)
    var iconName: String? = null

    fun onInfoChange(password: String, title: String) {
        btnSaveEnabled.set(title.isNotEmpty() && password.isNotEmpty() && iconName != null)
    }

    fun insert(passwordItemModel: PasswordItemModel) = viewModelScope.launch {
        passwordRepository.insert(passwordItemModel)
    }.invokeOnCompletion { err ->
        if (err == null) _insertLiveData.value = passwordItemModel
    }

}