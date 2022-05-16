package com.sn.secretive.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.sn.secretive.R
import com.sn.secretive.databinding.FragmentAboutBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.gone
import com.sn.secretive.extensions.visible
import nl.joery.animatedbottombar.AnimatedBottomBar
import android.content.Intent
import android.net.Uri


class AboutFragment : Fragment() {

    private val binding: FragmentAboutBinding by lazy {
        FragmentAboutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvLicenseUrl.click {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/emreesen27/Smooth-PDF-Viewer")
            )
            //todo change url
            startActivity(browserIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<AnimatedBottomBar>(R.id.bottom_bar).gone()
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().findViewById<AnimatedBottomBar>(R.id.bottom_bar).visible()
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

}