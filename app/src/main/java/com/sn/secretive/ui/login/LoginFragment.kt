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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var biometric: Biometric
    private lateinit var biometricListener: BiometricListener
    private val vm: LoginViewModel by viewModels()
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        biometric.subscribe(biometricListener)
    }

    override fun onPause() {
        super.onPause()
        biometric.unSubscribe()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initBiometric()
        hasFingerprintEnrolled()

        binding.btnLogin.click {
            when (vm.checkPIN(binding.etPin.text.toString())) {
                SUCCESS -> {
                    navigateHome()
                }
                WRONG_PIN -> {
                    binding.tvErr.visibleWithAnim(requireContext())
                    binding.tvErr.text = resources.getString(R.string.wrong_pin)
                }
                DB_ERR -> {
                    binding.tvErr.invisibleWithAnim(requireContext())
                    binding.tvErr.text = resources.getString(R.string.err_pin)
                }
            }

        }

        binding.ivFingerPrint.click {
            showBiometricDialog()
        }
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
            binding.ivFingerPrint.visible()
        else
            binding.ivFingerPrint.gone()
    }

    private fun initObserve() {
        vm.session.observe(viewLifecycleOwner) {
            binding.btnLogin.isEnabled = true
        }
    }

}