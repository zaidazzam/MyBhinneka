package org.d3if3055.mopro1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3055.mopro1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //TITLE
        supportActionBar?.title = "MyBhinneka"
        //SUBTITLE

        val bundle = intent.extras

        binding.nama.text = bundle?.getCharSequence(Login.NAMA)
        binding.alamat.text = bundle?.getCharSequence(Login.ASAL)

        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            ) // menambahkan garis divider
            adapter = MainAdapter(getData())
            setHasFixedSize(true)
        }
    }

    private fun getData(): List<Budaya> {
        return listOf(
            Budaya("Suku Sunda", "Jawa Barat", R.drawable.sukusunda),
            Budaya("Suku Jawa", "Jawa Tengah", R.drawable.suku_jawa),
            Budaya("Suku Betawi", "Jakarta", R.drawable.sukubetawi),
            Budaya("Pakaian Adat Kebaya", "Jawa Barat", R.drawable.kebaya),
            Budaya("Pakaian Adat Mojang Malaka", "Jawa Barat", R.drawable.mojangmelaka),
            Budaya("Pakaian Adat Pangsi", "Jawa Barat", R.drawable.adatpangsi),
            Budaya("Pakaian Adat Menak", "Jawa Barat", R.drawable.adatmenak),
            Budaya("Rumah Adat Julang Ngapak", "Jawa Barat", R.drawable.julangngapak),
            Budaya("Rumah Adat Krong Bade", "Aceh", R.drawable.krongbade),
            Budaya("Senjata Tradisional Baliung", "Jawa Barat", R.drawable.baliung),
            Budaya("Senjata Tradisional Rencong", "Aceh", R.drawable.senjatarencong),
            Budaya("Alat Musik Rebab", "Jawa Barat", R.drawable.rebab),
            Budaya("Alat Musik Karinding", "Jawa Barat", R.drawable.karinding),
        )
    }
}