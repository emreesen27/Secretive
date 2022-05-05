package com.sn.secretive.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sn.secretive.R
import com.sn.secretive.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val binding: ActivityNavBinding by lazy {
        ActivityNavBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.home_nav_host)
        initBottomBar()
    }

    private fun initBottomBar() {
        val popupMenu = PopupMenu(this, binding.root)
        popupMenu.inflate(R.menu.menu)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }
}