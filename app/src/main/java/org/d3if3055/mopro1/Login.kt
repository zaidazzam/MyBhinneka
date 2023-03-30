package org.d3if3055.mopro1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.d3if3055.mobpro1.MainActivity
import org.d3if3055.mopro1.databinding.ActivityLoginBinding
import org.d3if3055.mopro1.databinding.ActivitySplashScreen1Binding

class Login : AppCompatActivity() {
    companion object {
        val NAMA: String? = "nama"
        val ASAL: String? = "alamat"
    }

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inisiasi view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TITLE
        supportActionBar?.title = ("Masukan Data")
        binding.buttonMasuk.setOnClickListener {
            if (binding.nama.text!!.isEmpty() || binding.alamat.text!!.isEmpty()) {
                toast("Kolom Tidak Boleh Kosong", Toast.LENGTH_LONG)
            } else {
                toast("Sukses", Toast.LENGTH_LONG)
                val intent = Intent(this, MainActivity::class.java)
                val bundle = Bundle()

                bundle.putString(NAMA, binding.nama.text.toString())
                bundle.putString(ASAL, binding.alamat.text.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    private fun toast(message: String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, length).show()
    }
}