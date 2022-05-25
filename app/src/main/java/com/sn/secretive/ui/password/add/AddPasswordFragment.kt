package com.sn.secretive.ui.password.add

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sn.secretive.R
import com.sn.secretive.adapter.IconsAdapter
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.FragmentAddPasswordBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddPasswordFragment() : BaseFragment<AddPasswordViewModel, FragmentAddPasswordBinding>() {

    private val iconsAdapter by lazy {
        IconsAdapter(requireContext())
    }

    override fun getLayoutId(): Int = R.layout.fragment_add_password

    override fun getViewModel(): Lazy<AddPasswordViewModel> = viewModels()

    override fun bindViewModel(
        model: AddPasswordViewModel,
        dataBinding: FragmentAddPasswordBinding
    ) = with(dataBinding) {
        vm = model
        rcvIcons.adapter = iconsAdapter
        initObserve()

        etTitle.doOnTextChanged { title, _, _, _ ->
            model.onInfoChange(title.toString(), etPassword.text.toString())
        }

        etPassword.doOnTextChanged { password, _, _, _ ->
            model.onInfoChange(etTitle.text.toString(), password.toString())
        }

        iconsAdapter.onClick = { iconName ->
            model.iconName = iconName
            model.onInfoChange(etTitle.text.toString(), etPassword.text.toString())
        }

        btnSave.click {
            val password = PasswordItemModel(
                null,
                etTitle.text.toString(),
                etPassword.text.toString(),
                etNote.text.toString(),
                model.iconName!!
            )
            model.insert(password)
        }

    }

    private fun initObserve() {
        vModel().insertLiveData.observe(viewLifecycleOwner) { item ->
            val action = AddPasswordFragmentDirections.actionAddToSuccess(item)
            startAction(action)
            iconsAdapter.resetIcons()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    iconsAdapter.resetIcons()
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }
}