package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.ActivityMainBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.LoginActivity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.adapters.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        // startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun init(){
        initUI()
    }

    private fun initUI(){
        binding.viewPager.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                this
            )
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}