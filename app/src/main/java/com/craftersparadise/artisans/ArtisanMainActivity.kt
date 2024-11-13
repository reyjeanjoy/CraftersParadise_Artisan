package com.craftersparadise.artisans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.craftersparadise.artisans.databinding.ActivityArtisanMainBinding

class ArtisanMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtisanMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisanMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial fragment
        if (savedInstanceState == null) {
            // Set the default selected item to home
            binding.bottomNavView.selectedItemId = R.id.home
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.message -> {
                    replaceFragment(MessageFragment())
                    true
                }
                R.id.home -> {
                    replaceFragment(ArtisanHomeFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }
}
