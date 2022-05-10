package com.sn.secretive.ui.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sn.secretive.databinding.FragmentAddPassSuccessBinding
import com.sn.secretive.extensions.click

class AddPassSuccessFragment : Fragment() {

    private val args: AddPassSuccessFragmentArgs by navArgs()
    private val binding: FragmentAddPassSuccessBinding by lazy {
        FragmentAddPassSuccessBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPasswordName.text = args.argsPassName

        binding.btnFirst.click {
            requireActivity().finish()
        }


        binding.btnSecond.click {}

    }


}