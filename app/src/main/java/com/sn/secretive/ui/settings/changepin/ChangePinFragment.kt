package com.sn.secretive.ui.settings.changepin

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sn.secretive.R
import com.sn.secretive.databinding.FragmentChangePinBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.observe
import com.sn.secretive.extensions.showToast
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePinFragment : BaseFragment<ChangePinViewModel, FragmentChangePinBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_change_pin

    override fun getViewModel(): Lazy<ChangePinViewModel> = viewModels()

    override fun bindViewModel(
        model: ChangePinViewModel,
        dataBinding: FragmentChangePinBinding
    ) =
        with(dataBinding) {
            vm = model
            initObserve()

            etCurrentlyPin.doOnTextChanged { CurrentlyPin, _, _, _ ->
                model.onInfoChange(
                    CurrentlyPin.toString(),
                    etNewPin.text.toString(),
                    etConfirmPin.text.toString()
                )
            }

            etNewPin.doOnTextChanged { newPin, _, _, _ ->
                model.onInfoChange(
                    newPin.toString(),
                    etCurrentlyPin.text.toString(),
                    etConfirmPin.text.toString()
                )
            }

            etConfirmPin.doOnTextChanged { confirmPin, _, _, _ ->
                model.onInfoChange(
                    confirmPin.toString(),
                    etNewPin.text.toString(),
                    etConfirmPin.text.toString()
                )
            }

            btnSave.click {
                with(vModel()) {

                    if (!controlPin(etCurrentlyPin.text.toString())) {
                        context?.showToast(getString(R.string.wrong_pin), Toast.LENGTH_SHORT)
                        return@click
                    }

                    if (!pinsIsEquals(etNewPin.text.toString(), etConfirmPin.text.toString())) {
                        context?.showToast(
                            getString(R.string.pin_confirmation_error),
                            Toast.LENGTH_SHORT
                        )
                        return@click
                    }

                    updatePin(etCurrentlyPin.text.toString())
                }
            }
        }

    private fun initObserve() {
        observe(vModel().updatePinLiveData) { success ->
            if (success) context?.showToast(getString(R.string.successful), Toast.LENGTH_SHORT)
        }
    }
}
