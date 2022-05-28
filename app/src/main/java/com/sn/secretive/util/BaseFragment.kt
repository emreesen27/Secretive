package com.sn.secretive.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<V : ViewModel, B : ViewDataBinding> : Fragment() {

    private lateinit var viewModel: V
    private lateinit var dataBinding: B

    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): Lazy<V>

    abstract fun bindViewModel(model: V, dataBinding: B)

    fun getBinding(): B = dataBinding

    fun vModel() = getViewModel().value

    fun startAction(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel().value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel(viewModel, dataBinding)
    }

}