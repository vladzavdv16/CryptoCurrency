package com.light.cryptocurrency.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val findNavController: NavController = Navigation.findNavController(this, R.id.main_host)
        NavigationUI.setupWithNavController(binding.bottomNavMenu, findNavController)
        NavigationUI.setupWithNavController(
            binding.toolbar,
            findNavController,
            AppBarConfiguration.Builder(binding.bottomNavMenu.menu).build()
        )

    }


}