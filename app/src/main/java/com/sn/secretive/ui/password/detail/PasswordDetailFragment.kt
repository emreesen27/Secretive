package com.sn.secretive.ui.password.detail

import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sn.secretive.R
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.BottomSheetUpdateBinding
import com.sn.secretive.databinding.FragmentPasswordDetailBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.getIcon
import com.sn.secretive.extensions.showToast
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailFragment :
    BaseFragment<PasswordDetailViewModel, FragmentPasswordDetailBinding>() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var itemP: PasswordItemModel

    override fun getLayoutId(): Int = R.layout.fragment_password_detail

    override fun getViewModel(): Lazy<PasswordDetailViewModel> = viewModels()

    override fun bindViewModel(
        model: PasswordDetailViewModel,
        dataBinding: FragmentPasswordDetailBinding
    ) = with(dataBinding) {

        itemP = arguments?.get("passwordItem") as PasswordItemModel
        item = itemP
        ivIcon.setImageResource(requireContext().getIcon(itemP.iconName))

        dvTitle.onClick =
            { showBottomSheet(PasswordDetailViewModel.TITLE, dvTitle.value) }
        dvPassword.onClick =
            { showBottomSheet(PasswordDetailViewModel.PASSWORD, dvPassword.value) }
        dvNote.onClick =
            { showBottomSheet(PasswordDetailViewModel.NOTE, dvNote.value) }

        model.updateLiveData.observe(viewLifecycleOwner) { item ->
            dvTitle.value = item.title
            dvPassword.value = item.password
            dvNote.value = item.note.toString()
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

            val item = PasswordItemModel(itemP.id, title, pass, note, itemP.iconName)
            vModel().update(item)
        }
        bottomSheetDialog.show()
    }

}