package com.dicoding.nutriscan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.capstone.nutriscan.R
import com.capstone.nutriscan.databinding.ActivityMainBottomBinding

class MainActivityBottom : AppCompatActivity() {

    private lateinit var binding: ActivityMainBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_bottom)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
