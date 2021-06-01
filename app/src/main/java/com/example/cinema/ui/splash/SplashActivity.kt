package com.example.cinema.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.cinema.MainActivity
import com.example.cinema.databinding.ActivitySplashBinding
import com.example.cinema.ui.login.LoginActivity
import com.example.cinema.utils.Profile

class SplashActivity : AppCompatActivity() {
    companion object {
        const val DURATION_DELAY : Int = 3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences(Profile.KEY_APP, Context.MODE_PRIVATE)
        val name = sharedPref.getString(Profile.KEY_NAME, Profile.NULL_VALUE)
        val gender = sharedPref.getString(Profile.KEY_GENDER, Profile.NULL_VALUE)

        splashBinding.progressBarSplash.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            if (name.equals(Profile.NULL_VALUE) && gender.equals(Profile.NULL_VALUE)){
                val i = Intent(applicationContext, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }, DURATION_DELAY.toLong())
    }
}