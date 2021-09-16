package com.light.cryptocurrency.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.light.cryptocurrency.App
import com.light.cryptocurrency.BaseComponent
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var component: MainComponent

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        val baseComponent: BaseComponent = (newBase!!.applicationContext as App).component
        component = DaggerMainComponent.builder().baseComponent(baseComponent).build()
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
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