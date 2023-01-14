package com.sn.secretive.ui.password.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sn.secretive.R
import com.sn.secretive.components.imagepicker.ImagePicker
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.FragmentPasswordDetailBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.observe
import com.sn.secretive.extensions.showToast
import com.sn.secretive.ui.password.PasswordActivity
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailFragment :
    BaseFragment<PasswordDetailViewModel, FragmentPasswordDetailBinding>() {

    private lateinit var iconSelectedListener: ImagePicker.ItemSelectedListener
    private lateinit var passwordItemModel: PasswordItemModel

    override fun getLayoutId(): Int = R.layout.fragment_password_detail

    override fun getViewModel(): Lazy<PasswordDetailViewModel> = viewModels()

    override fun bindViewModel(
        model: PasswordDetailViewModel,
        dataBinding: FragmentPasswordDetailBinding
    ) = with(dataBinding) {

        initObserve(dataBinding)

        passwordItemModel = arguments?.get(PasswordActivity.PASSWORD_ITEM_KEY) as PasswordItemModel
        item = passwordItemModel
        model.setIconName(passwordItemModel.iconName)
        imagePicker.setSelectImage(passwordItemModel.iconName)

        iconSelectedListener = object : ImagePicker.ItemSelectedListener {
            override fun onSelected(imageName: String) {
                model.setIconName(imageName)
            }
        }

        btnSave.click {
            if (dvTitle.value.isEmpty() || dvPassword.value.isEmpty()) {
                context?.showToast(getString(R.string.empty_err_messages), Toast.LENGTH_SHORT)
                return@click
            }
            passwordItemModel.id?.let {
                vModel().setUpdateArgument(
                    it,
                    dvTitle.value,
                    dvPassword.value,
                    dvNote.value
                )
            }
        }
    }

    private fun initObserve(binding: FragmentPasswordDetailBinding) {
        with(binding) {
            observe(vModel().updateLiveData) { item ->
                dvTitle.value = item.title
                dvPassword.value = item.password
                dvNote.value = item.note.toString()
                context?.showToast(getString(R.string.successful), Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getBinding().imagePicker.subscribe(iconSelectedListener)
    }

    override fun onPause() {
        super.onPause()
        getBinding().imagePicker.unSubscribe()
    }
}
