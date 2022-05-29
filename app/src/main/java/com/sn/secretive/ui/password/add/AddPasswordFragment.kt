package com.sn.secretive.ui.password.add

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sn.secretive.R
import com.sn.secretive.components.IconPicker
import com.sn.secretive.databinding.FragmentAddPasswordBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.observe
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddPasswordFragment() : BaseFragment<AddPasswordViewModel, FragmentAddPasswordBinding>() {

    private lateinit var iconSelectedListener: IconPicker.ItemSelectedListener

    override fun getLayoutId(): Int = R.layout.fragment_add_password

    override fun getViewModel(): Lazy<AddPasswordViewModel> = viewModels()

    override fun bindViewModel(
        model: AddPasswordViewModel,
        dataBinding: FragmentAddPasswordBinding
    ) = with(dataBinding) {
        vm = model
        initObserve()

        etTitle.doOnTextChanged { title, _, _, _ ->
            model.onInfoChange(title.toString(), etPassword.text.toString())
        }

        etPassword.doOnTextChanged { password, _, _, _ ->
            model.onInfoChange(etTitle.text.toString(), password.toString())
        }

        iconSelectedListener = object : IconPicker.ItemSelectedListener {
            override fun onSelected(iconName: String) {
                model.setIconName(iconName)
                model.onInfoChange(etTitle.text.toString(), etPassword.toString())
            }
        }

        btnSave.click {
            model.setInsertArgument(
                etTitle.text.toString(),
                etPassword.text.toString(),
                etNote.text.toString()
            )
        }

    }

    private fun initObserve() {
        observe(vModel().insertLiveData) { item ->
            val action = AddPasswordFragmentDirections.actionAddToSuccess(item)
            startAction(action)
        }
    }

    override fun onResume() {
        super.onResume()
        getBinding().iconPicker.subscribe(iconSelectedListener)

    }

    override fun onPause() {
        super.onPause()
        getBinding().iconPicker.unSubscribe()
    }
}