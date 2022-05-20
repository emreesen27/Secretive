package com.sn.secretive.ui.password.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailFragment : Fragment() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var item: PasswordItemModel
    private val vm: PasswordDetailViewModel by viewModels()
    private val binding: FragmentPasswordDetailBinding by lazy {
        FragmentPasswordDetailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = arguments?.get("passwordItem") as PasswordItemModel
        binding.item = item
        binding.ivIcon.setImageResource(requireContext().getIcon(item.iconName))

        binding.dvTitle.onClick =
            { showBottomSheet(PasswordDetailViewModel.TITLE, binding.dvTitle.value) }
        binding.dvPassword.onClick =
            { showBottomSheet(PasswordDetailViewModel.PASSWORD, binding.dvPassword.value) }
        binding.dvNote.onClick =
            { showBottomSheet(PasswordDetailViewModel.NOTE, binding.dvNote.value) }

        vm.updateLiveData.observe(viewLifecycleOwner) { item ->
            binding.dvTitle.value = item.title
            binding.dvPassword.value = item.password
            binding.dvNote.value = item.note.toString()
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

        var title = binding.dvTitle.value
        var pass = binding.dvPassword.value
        var note = binding.dvNote.value
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
                context?.showToast("Değer boş bırakılamaz !", Toast.LENGTH_SHORT)
                return@click
            }

            val item = PasswordItemModel(item.id, title, pass, note, item.iconName)
            vm.update(item)
        }
        bottomSheetDialog.show()
    }

}