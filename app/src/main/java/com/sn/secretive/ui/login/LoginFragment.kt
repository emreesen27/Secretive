package com.sn.secretive.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sn.biometric.Biometric
import com.sn.biometric.BiometricListener
import com.sn.secretive.ui.login.LoginViewModel.Companion.DB_ERR
import com.sn.secretive.ui.login.LoginViewModel.Companion.SUCCESS
import com.sn.secretive.ui.login.LoginViewModel.Companion.WRONG_PIN
import com.sn.secretive.R
import com.sn.secretive.databinding.FragmentLoginBinding
import com.sn.secretive.extensions.*
import com.sn.secretive.ui.NavActivity
import com.sn.secretive.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private lateinit var biometric: Biometric
    private lateinit var biometricListener: BiometricListener

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getViewModel(): Lazy<LoginViewModel> = viewModels()

    override fun bindViewModel(model: LoginViewModel, dataBinding: FragmentLoginBinding) =
        with(dataBinding) {
            initObserve()
            initBiometric()
            hasFingerprintEnrolled()

            ivFingerPrint.click {
                showBiometricDialog()
            }

            btnLogin.click {
                when (model.checkPIN(etPin.text.toString())) {
                    SUCCESS -> {
                        navigateHome()
                    }
                    WRONG_PIN -> {
                        tvErr.visibleWithAnim(requireContext())
                        tvErr.text = resources.getString(R.string.wrong_pin)
                    }
                    DB_ERR -> {
                        tvErr.invisibleWithAnim(requireContext())
                        tvErr.text = resources.getString(R.string.err_pin)
                    }
                }
            }


        }

    override fun onResume() {
        super.onResume()
        biometric.subscribe(biometricListener)
    }

    override fun onPause() {
        super.onPause()
        biometric.unSubscribe()
    }


    private fun initBiometric() {
        biometric = Biometric(requireContext())
        biometricListener = object : BiometricListener {
            override fun onFingerprintAuthenticationSuccess() {
                navigateHome()
            }

            override fun onFingerprintAuthenticationFailure(errorMessage: String, errorCode: Int) {
                //Todo show err msg
            }
        }
        biometric.subscribe(biometricListener)
    }

    private fun showBiometricDialog() {
        biometric.showDialog(
            activity = requireActivity(),
            strings = Biometric.DialogStrings(
                title = resources.getString(R.string.login_finger_print),
                subTitle = "",
                description = "",
            )
        )
    }

    private fun navigateHome() {
        requireActivity().finish()
        startActivity(Intent(requireContext(), NavActivity::class.java))
    }

    private fun hasFingerprintEnrolled() {
        if (biometric.hasFingerprintEnrolled())
            getBinding().ivFingerPrint.visible()
        else
            getBinding().ivFingerPrint.gone()
    }

    private fun initObserve() {
        observe(vModel().session) {
            getBinding().btnLogin.isEnabled = true
        }
    }

}