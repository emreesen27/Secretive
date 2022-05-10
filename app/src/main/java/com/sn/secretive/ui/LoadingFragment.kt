package com.sn.secretive.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sn.secretive.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {

    private val binding: FragmentLoadingBinding by lazy {
        FragmentLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}