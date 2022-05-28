package com.sn.secretive.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sn.secretive.databinding.FragmentAboutBinding
import com.sn.secretive.extensions.click
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
                Uri.parse(SettingsUtils.SECRETIVE_REPOSITORY_LINK)
            )
            startActivity(browserIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}