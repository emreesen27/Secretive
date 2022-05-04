package com.sn.secretive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sn.secretive.databinding.LoadingFragmentBinding

class LoadingFragment : Fragment() {

    private val binding: LoadingFragmentBinding by lazy {
        LoadingFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}