package com.tlh.afinal

import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.tlh.afinal.databinding.ActivitySecondBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class SecondActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolBar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolBar = binding.toolbar
        setSupportActionBar(toolBar)
        drawerLayout = binding.drawerLayout
        navView = binding.navView

        // Navigation Controller setup
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up NavigationView listener
        navView.setNavigationItemSelectedListener(this)

        // Set up ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set default start destination
        if (savedInstanceState == null) {
            navController.navigate(R.id.homeScreenFragment)
            navView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeScreenFragment)
            R.id.nav_categories -> navController.navigate(R.id.categoriesFragment)
            R.id.nav_searchprod -> navController.navigate(R.id.searchProductFragment)
            R.id.nav_favprod -> navController.navigate(R.id.favoriteProductsFragment)
            R.id.nav_orders -> navController.navigate(R.id.myOrdersFragment)
            R.id.nav_profile -> navController.navigate(R.id.profileFragment)
            R.id.nav_cart -> navController.navigate(R.id.cartFragment)
            R.id.nav_exit -> exitProcess(0)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
