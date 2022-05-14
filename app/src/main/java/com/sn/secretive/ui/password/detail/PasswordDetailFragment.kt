package com.sn.secretive.ui.password.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.FragmentPasswordDetailBinding

class PasswordDetailFragment : Fragment() {

    private val vm: PasswordDetailViewModel by viewModels()
    private val binding: FragmentPasswordDetailBinding by lazy {
        FragmentPasswordDetailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val project = arguments?.get("passwordItem") as PasswordItemModel
        binding.project = project
    }


}