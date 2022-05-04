package com.sn.secretive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sn.secretive.data.model.SessionModel
import com.sn.secretive.databinding.PinFragmentBinding
import com.sn.secretive.extensions.click
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PinFragment : Fragment() {

    private lateinit var navigator: NavController
    private val vm: PinViewModel by viewModels()
    private val binding: PinFragmentBinding by lazy {
        PinFragmentBinding.inflate(layoutInflater)
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

        binding.etPin.doOnTextChanged { text, _, _, _ ->
            binding.btnContinue.isEnabled = text?.length == 4
        }

        binding.btnContinue.click {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = sdf.format(Date())
            val userModel = SessionModel(binding.etPin.text.toString(), currentDate)
            vm.insert(userModel)
            navigator.navigate(PinFragmentDirections.actionPinToLogin())
        }


    }


}