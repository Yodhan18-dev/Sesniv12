package com.bdkjupiter.sensi.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bdkjupiter.sensi.R
import com.bdkjupiter.sensi.databinding.ActivityMainBinding
import com.bdkjupiter.sensi.ui.advanced.AdvancedFragment
import com.bdkjupiter.sensi.ui.generate.GenerateFragment
import com.bdkjupiter.sensi.ui.history.HistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_generate -> loadFragment(GenerateFragment())
                R.id.nav_advanced -> loadFragment(AdvancedFragment())
                R.id.nav_history -> loadFragment(HistoryFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
