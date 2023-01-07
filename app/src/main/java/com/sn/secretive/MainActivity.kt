package com.sn.secretive

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sn.secretive.databinding.ActivityMainBinding
import com.sn.secretive.ui.settings.SettingsUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setLocalTheme()

        vm.sessionLiveData.observe(this) { user ->
            setNavigationGraph(if (user == null) R.id.pin_fragment else R.id.LoginFragment)
        }
    }

    private fun setLocalTheme() {
        val theme = SettingsUtils.resolveThemeMode(this)
        SettingsUtils.changeTheme(theme)
    }

    private fun setNavigationGraph(startDestId: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.base_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.base_nav_graph)
        navGraph.setStartDestination(startDestId)
        navController.graph = navGraph
    }
}
