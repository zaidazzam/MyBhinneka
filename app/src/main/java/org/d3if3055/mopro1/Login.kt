package org.d3if3055.mopro1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if3055.mopro1.databinding.ActivityLoginBinding
import org.d3if3055.mopro1.databinding.ActivitySplashScreen1Binding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisiasi view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}