package com.sn.secretive.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sn.secretive.R
import com.sn.secretive.databinding.FragmentHomeBinding
import com.sn.secretive.ui.password.PasswordActivity
import com.sn.secretive.adapter.PasswordAdapter
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.BottomSheetDeleteBinding
import com.sn.secretive.extensions.*
import com.sn.secretive.ui.password.PasswordFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var navigator: NavController
    private val vm: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val passwordAdapter by lazy {
        PasswordAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = findNavController()
        initRecyclerview()

        binding.floatActionButton.click { navigateAddPass() }
        binding.tvAddPassword.click { navigateAddPass() }

        vm.passwordLiveData.observe(viewLifecycleOwner) { passwords ->
            if (passwords.isEmpty()) binding.noPassGroup.visible() else binding.noPassGroup.gone()
            passwordAdapter.items = passwords
        }

        vm.deletePassLiveData.observe(viewLifecycleOwner) { position ->
            passwordAdapter.notifyItemRemoved(position)
        }

    }

    private fun initRecyclerview() {
        binding.rcvPass.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rcvPass.adapter = passwordAdapter
        passwordAdapter.onClick = { passwordItem ->
            navigatePassDetail(passwordItem)
        }

        passwordAdapter.onLongClick = { passwordItem, position ->
            showBottomSheet(passwordItem, position)
        }
    }


    private fun navigateAddPass() {
        val intent = Intent(requireContext(), PasswordActivity::class.java)
        intent.putExtra("flow", PasswordFlow.ADD_PASSWORD.flow)
        startActivity(intent)
    }

    private fun navigatePassDetail(item: PasswordItemModel) {
        val intent = Intent(requireContext(), PasswordActivity::class.java)
        intent.putExtra("flow", PasswordFlow.DETAIL_PASSWORD.flow)
        intent.putExtra("passwordItem", item)
        startActivity(intent)
    }

    private fun showBottomSheet(item: PasswordItemModel, pos: Int) {
        bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bindingSheet = DataBindingUtil.inflate<BottomSheetDeleteBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.bottom_sheet_delete,
            null,
            false
        )
        bottomSheetDialog.setContentView(bindingSheet.root)
        bindingSheet.ivClose.click { bottomSheetDialog.dismiss() }
        bindingSheet.btnDelete.click {
            vm.deletePassword(item, pos)
            bottomSheetDialog.dismiss()
        }
        bindingSheet.btnDetail.click {
            navigatePassDetail(item)
            bottomSheetDialog.dismiss()
        }
        bindingSheet.btnCopy.click {
            bottomSheetDialog.dismiss()
            requireContext().copyToClipboard(item.password)
            requireContext().showToast(getString(R.string.password_copied), Toast.LENGTH_LONG)
        }
        bottomSheetDialog.show()

    }


}