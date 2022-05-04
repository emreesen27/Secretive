package com.sn.secretive

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sn.secretive.LoginViewModel.Companion.DB_ERR
import com.sn.secretive.LoginViewModel.Companion.SUCCESS
import com.sn.secretive.LoginViewModel.Companion.WRONG_PIN
import com.sn.secretive.databinding.LoginFragmentBinding
import com.sn.secretive.extensions.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val vm: LoginViewModel by viewModels()
    private val binding by lazy {
        LoginFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()

        binding.btnContinue.click {
            when (vm.checkPIN(binding.etPin.text.toString())) {
                SUCCESS -> {
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
                WRONG_PIN -> {
                    //todo wrong pin
                }
                DB_ERR -> {
                    //todo db error
                }
            }

        }

        binding.ivFingerPrint.click {
            //fingerprint
        }


    }

    private fun initObserve() {
        vm.session.observe(viewLifecycleOwner) {
            binding.btnContinue.isEnabled = true
        }
    }

}