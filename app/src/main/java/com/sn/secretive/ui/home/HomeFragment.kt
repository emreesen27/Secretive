package com.sn.secretive.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sn.secretive.R
import com.sn.secretive.adapter.PasswordAdapter
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.BottomSheetDeleteBinding
import com.sn.secretive.databinding.FragmentHomeBinding
import com.sn.secretive.extensions.* // ktlint-disable no-wildcard-imports other-rule-id
import com.sn.secretive.ui.password.PasswordActivity
import com.sn.secretive.ui.password.PasswordFlow
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val passwordAdapter by lazy {
        PasswordAdapter(requireContext())
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getViewModel(): Lazy<HomeViewModel> = viewModels()

    override fun bindViewModel(model: HomeViewModel, dataBinding: FragmentHomeBinding) =
        with(dataBinding) {
            initObserve()
            initRecyclerview()

            floatActionButton.click { navigateAddPass() }
            tvAddPassword.click { navigateAddPass() }
        }

    private fun initObserve() {
        observe(vModel().passwordLiveData) { passwords ->
            if (passwords.isEmpty()) getBinding().noPassGroup.visible() else getBinding().noPassGroup.gone()
            passwordAdapter.items = passwords
        }

        observe(vModel().deletePassLiveData) { position ->
            passwordAdapter.notifyItemRemoved(position)
        }
    }

    private fun initRecyclerview() {
        getBinding().rcvPass.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        getBinding().rcvPass.adapter = passwordAdapter
        passwordAdapter.onClick = { passwordItem ->
            navigatePassDetail(passwordItem)
        }

        passwordAdapter.onLongClick = { passwordItem, position ->
            showBottomSheet(passwordItem, position)
        }
    }

    private fun navigateAddPass() {
        val intent = Intent(requireContext(), PasswordActivity::class.java)
        intent.putExtra(PasswordActivity.PASSWORD_FLOW_KEY, PasswordFlow.ADD_PASSWORD.flow)
        startActivity(intent)
    }

    private fun navigatePassDetail(item: PasswordItemModel) {
        val intent = Intent(requireContext(), PasswordActivity::class.java)
        intent.putExtra(PasswordActivity.PASSWORD_FLOW_KEY, PasswordFlow.DETAIL_PASSWORD.flow)
        intent.putExtra(PasswordActivity.PASSWORD_ITEM_KEY, item)
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
            vModel().deletePassword(item, pos)
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
