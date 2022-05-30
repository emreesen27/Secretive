package com.sn.secretive.ui.password.detail

import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sn.secretive.R
import com.sn.secretive.components.IconPicker
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.BottomSheetUpdateBinding
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

    private lateinit var iconSelectedListener: IconPicker.ItemSelectedListener
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var passwordItemModel: PasswordItemModel

    override fun getLayoutId(): Int = R.layout.fragment_password_detail

    override fun getViewModel(): Lazy<PasswordDetailViewModel> = viewModels()

    override fun bindViewModel(
        model: PasswordDetailViewModel,
        dataBinding: FragmentPasswordDetailBinding
    ) = with(dataBinding) {

        initObserve()
        passwordItemModel = arguments?.get(PasswordActivity.PASSWORD_ITEM_KEY) as PasswordItemModel
        item = passwordItemModel
        model.setIconName(passwordItemModel.iconName)
        iconPicker.setImage(passwordItemModel.iconName)

        dvTitle.onClick =
            { showBottomSheet(PasswordDetailViewModel.TITLE, dvTitle.value) }
        dvPassword.onClick =
            { showBottomSheet(PasswordDetailViewModel.PASSWORD, dvPassword.value) }
        dvNote.onClick =
            { showBottomSheet(PasswordDetailViewModel.NOTE, dvNote.value) }


        iconSelectedListener = object : IconPicker.ItemSelectedListener {
            override fun onSelected(iconName: String) {
                model.setIconName(iconName)
                passwordItemModel.id?.let {
                    model.setUpdateArgument(
                        it,
                        getBinding().dvTitle.value,
                        getBinding().dvPassword.value,
                        getBinding().dvNote.value
                    )
                }
            }
        }

    }

    private fun initObserve() {
        observe(vModel().updateLiveData) { item ->
            getBinding().dvTitle.value = item.title
            getBinding().dvPassword.value = item.password
            getBinding().dvNote.value = item.note.toString()
        }
    }

    private fun showBottomSheet(tag: String, oldValue: String) {
        bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bindingSheet = DataBindingUtil.inflate<BottomSheetUpdateBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.bottom_sheet_update,
            null,
            false
        )
        bottomSheetDialog.setContentView(bindingSheet.root)
        bindingSheet.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }

        var title = getBinding().dvTitle.value
        var pass = getBinding().dvPassword.value
        var note = getBinding().dvNote.value
        bindingSheet.etUpdate.setText(oldValue)

        bindingSheet.btnUpdate.click {
            val value = bindingSheet.etUpdate.text.toString()
            bottomSheetDialog.dismiss()
            when (tag) {
                PasswordDetailViewModel.TITLE -> {
                    title = value
                }
                PasswordDetailViewModel.PASSWORD -> {
                    pass = value
                }
                PasswordDetailViewModel.NOTE -> {
                    note = value
                }
            }

            if (title.isEmpty() || pass.isEmpty()) {
                context?.showToast(getString(R.string.empty_err_messages), Toast.LENGTH_SHORT)
                return@click
            }

            passwordItemModel.id?.let {
                vModel().setUpdateArgument(
                    it,
                    title,
                    pass,
                    note
                )
            }
        }
        bottomSheetDialog.show()
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