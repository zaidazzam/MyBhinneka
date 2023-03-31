package org.d3if3055.mopro1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if3055.mopro1.databinding.ActivitySplashScreen2Binding

class SplashScreen2 : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreen2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisiasi view binding
        binding = ActivitySplashScreen2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLanjut.setOnClickListener { lanjut() }
    }

    private fun lanjut() {
        val lanjut = Intent(this@SplashScreen2, Login::class.java)
        startActivity(lanjut)
        finish()
    }
}