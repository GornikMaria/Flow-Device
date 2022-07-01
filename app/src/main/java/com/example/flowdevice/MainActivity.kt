package com.example.flowdevice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller = findNavController(R.id.nav_host_fragment)
        val menuView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        menuView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_phone -> {
                    controller.navigate(R.id.devicesFragment)
                }
                R.id.action_arm -> {
                    controller.navigate(R.id.takenFragment)
                }
                R.id.action_history -> {
                    controller.navigate(R.id.historyFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

}

