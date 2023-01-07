package com.sn.secretive.ui.password.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.FragmentAddPassSuccessBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.ui.password.PasswordActivity
import com.sn.secretive.ui.password.PasswordFlow

class AddPassSuccessFragment : Fragment() {

    private val args: AddPassSuccessFragmentArgs by navArgs()
    private val binding: FragmentAddPassSuccessBinding by lazy {
        FragmentAddPassSuccessBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPasswordName.text = args.argsPass.title

        binding.btnFirst.click {
            requireActivity().finish()
        }

        binding.btnSecond.click {
            requireActivity().finish()
            navigatePassDetail(args.argsPass)
        }
    }

    private fun navigatePassDetail(item: PasswordItemModel) {
        val intent = Intent(requireContext(), PasswordActivity::class.java)
        intent.putExtra("flow", PasswordFlow.DETAIL_PASSWORD.flow)
        intent.putExtra("passwordItem", item)
        startActivity(intent)
    }
}
