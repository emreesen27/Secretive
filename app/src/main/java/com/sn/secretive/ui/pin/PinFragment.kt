package com.sn.secretive.ui.pin

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sn.secretive.R
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.databinding.FragmentPinBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.hideKeyboard
import com.sn.secretive.util.BaseFragment
import com.sn.secretive.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinFragment : BaseFragment<PinViewModel, FragmentPinBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_pin

    override fun getViewModel(): Lazy<PinViewModel> = viewModels()

    override fun bindViewModel(model: PinViewModel, dataBinding: FragmentPinBinding) =
        with(dataBinding) {

            etPin.doOnTextChanged { text, _, _, _ ->
                btnContinue.isEnabled = text?.length == 4
            }

            btnContinue.click {
                etPin.hideKeyboard()
                val userModel = SessionModel(etPin.text.toString(), DateUtil.getCurrentDate())
                model.insert(userModel)
                startAction(PinFragmentDirections.actionPinToLogin())
            }
        }
}
