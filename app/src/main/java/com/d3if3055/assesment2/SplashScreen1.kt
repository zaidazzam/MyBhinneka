package com.d3if3055.assesment2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d3if3055.assesment2.databinding.ActivitySplashScreen1Binding

class SplashScreen1 : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreen1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisiasi view binding
        binding = ActivitySplashScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLanjut.setOnClickListener { lanjut() }
    }

    private fun lanjut() {
        val lanjut = Intent(this@SplashScreen1, SplashScreen2::class.java)
        startActivity(lanjut)
        finish()
    }
}