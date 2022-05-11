package com.sn.secretive.ui.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.sn.secretive.R
import com.sn.secretive.data.model.PasswordItemModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val flow = intent.getStringExtra("flow")
        val passwordItem = intent.getParcelableExtra<PasswordItemModel>("passwordItem")
        val bundle = bundleOf(
            "passwordItem" to passwordItem
        )
        setNavigationGraph(
            if (flow == PasswordFlow.ADD_PASSWORD.flow) R.id.add_password_fragment else R.id.detail_fragment,
            bundle
        )
    }

    private fun setNavigationGraph(startDestId: Int, bundle: Bundle) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.password_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.password_graph)
        navGraph.setStartDestination(startDestId)
        navController.setGraph(navGraph, bundle)

    }

}