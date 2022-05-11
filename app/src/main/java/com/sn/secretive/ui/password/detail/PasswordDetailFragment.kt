package com.sn.secretive.ui.password.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sn.secretive.R

class PasswordDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PasswordDetailFragment()
    }

    private lateinit var viewModel: PasswordDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PasswordDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}