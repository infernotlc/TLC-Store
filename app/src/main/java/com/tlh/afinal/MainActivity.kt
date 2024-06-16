package com.tlh.afinal

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.fragment
import com.tlh.afinal.databinding.ActivityMainBinding
import com.tlh.afinal.screens.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContent {
            val navController = rememberNavController()
            navController.navigatorProvider.addNavigator(
                FragmentNavigator(this, supportFragmentManager)
            )
            NavHost(navController = navController, startDestination = LOGIN_SCREEN) {
                fragment<HomeScreen>(HOME_SCREEN) {
                    HomeScreen()
                }
            }
            FinalApp()
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
//    }
}