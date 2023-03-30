package org.d3if3055.mopro1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if3055.mopro1.databinding.ActivityMainBinding
import org.d3if3055.mopro1.databinding.ActivitySplashScreen1Binding

class SplashScreen1 : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreen1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisiasi view binding
        binding = ActivitySplashScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}