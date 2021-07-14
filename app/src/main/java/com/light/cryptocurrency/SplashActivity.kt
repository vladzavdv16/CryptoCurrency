package com.light.cryptocurrency

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.preference.PreferenceManager

class SplashActivity : AppCompatActivity() {

    private val handler: Handler = Handler()
    private lateinit var prefs: SharedPreferences
    private lateinit var runnable: Runnable
    val KEY_SHOW = "key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (prefs.getBoolean(KEY_SHOW, true)) {
            runnable = Runnable {
                startActivity(
                    Intent(
                        this@SplashActivity, WelcomeActivity::class.java
                    )
                )
            }
        } else {
            runnable = Runnable {
                startActivity(
                    Intent(
                        this@SplashActivity, MainActivity::class.java
                    )
                )
            }
        }

        handler.postDelayed(runnable, 2000)
    }


    override fun onStop() {
        handler.removeCallbacks(runnable)
        super.onStop()
    }

}