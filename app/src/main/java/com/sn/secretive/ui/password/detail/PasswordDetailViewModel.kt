package com.sn.secretive.ui.password.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.data.repository.PasswordRepository
import com.sn.secretive.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {

    private var iconName: String? = null
    private val _updateLiveData: MutableLiveData<PasswordItemModel> = MutableLiveData()
    val updateLiveData: LiveData<PasswordItemModel> get() = _updateLiveData

    private fun update(passwordItemModel: PasswordItemModel) = viewModelScope.launch {
        passwordRepository.update(passwordItemModel)
    }.invokeOnCompletion { err ->
        if (err == null) _updateLiveData.value = passwordItemModel
    }

    fun setIconName(iconName: String) {
        this.iconName = iconName
    }

    fun setUpdateArgument(id: Int, title: String, password: String, note: String?) {
        val item =
            PasswordItemModel(id, title, password, note, iconName!!, DateUtil.getCurrentDate())
        update(item)
    }
}
