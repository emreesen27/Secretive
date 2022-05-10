package com.sn.secretive.ui.password


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

    private val _insertLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val insertLiveData: LiveData<Boolean> get() = _insertLiveData

    val btnSaveEnabled = ObservableBoolean(false)
    var iconID: Int? = null

    fun onInfoChange(password: String, title: String) {
        btnSaveEnabled.set(title.isNotEmpty() && password.isNotEmpty() && iconID != null)
    }

    fun insert(passwordItemModel: PasswordItemModel) = viewModelScope.launch {
        passwordRepository.insert(passwordItemModel)
    }.invokeOnCompletion { err ->
        _insertLiveData.value = err == null
    }

}