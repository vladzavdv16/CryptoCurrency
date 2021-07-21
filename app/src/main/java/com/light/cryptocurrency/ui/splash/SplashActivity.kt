package com.light.cryptocurrency.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.preference.PreferenceManager
import com.light.cryptocurrency.R
import com.light.cryptocurrency.ui.main.MainActivity
import com.light.cryptocurrency.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    private val handler: Handler = Handler()
    private lateinit var prefs: SharedPreferences
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (prefs.getBoolean(WelcomeActivity.KEY_SHOW, true)) {
            runnable = Runnable {
                startActivity(
                    Intent(
                        this, WelcomeActivity::class.java
                    )
                )
            }
        } else {
            runnable = Runnable {
                startActivity(
                    Intent(
                        this, MainActivity::class.java
                    )
                )
            }
        }

        handler.postDelayed(runnable, 3000)
    }


    override fun onStop() {
        handler.removeCallbacks(runnable)
        super.onStop()
    }

}