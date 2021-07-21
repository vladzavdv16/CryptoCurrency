package com.light.cryptocurrency.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.light.cryptocurrency.R
import com.light.cryptocurrency.databinding.ActivityWelcomeBinding
import com.light.cryptocurrency.ui.main.MainActivity
import com.light.cryptocurrency.widget.CircleIndicator

class WelcomeActivity : AppCompatActivity() {

    companion object {
        val KEY_SHOW = "key"
    }

    private lateinit var helper: SnapHelper
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.addItemDecoration(CircleIndicator(this))
        binding.recycler.adapter = WelcomeAdapter()

        helper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.recycler)

        binding.btnWelcome.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean(KEY_SHOW, false).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        binding.recycler.adapter = null
        helper.attachToRecyclerView(null)
        super.onDestroy()
    }
}