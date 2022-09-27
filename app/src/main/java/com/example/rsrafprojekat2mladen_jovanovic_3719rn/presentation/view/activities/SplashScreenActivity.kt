package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val rocket = findViewById<ImageView>(R.id.rocket)
        rocket.alpha = 0f
        rocket.animate().setDuration(1500).alpha(1f).withEndAction{
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}