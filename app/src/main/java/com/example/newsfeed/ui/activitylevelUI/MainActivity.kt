package com.example.newsfeed.ui.activitylevelUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.newsfeed.R
import com.example.newsfeed.databinding.ActivityMainBinding
import com.example.newsfeed.util.handleDrawerNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.mainToolbar)

        setupDrawer()

        setupBottomNavigation()

        navigationVisibilityElements(navController)

    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> super.onBackPressed()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupDrawer() {
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.newsScreenFragment,
            R.id.searchNewsFragment,
            R.id.savedNewsFragment)
            .setOpenableLayout(binding.drawerLayout)
            .build()

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.drawerNavigationView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            handleDrawerNavigation(menuItem, viewModel, navController)
            true
        }
    }


    private fun navigationVisibilityElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.articleFragment -> hideBottomNavigation()
                R.id.newsScreenFragment -> showBottomNavigation()
                R.id.savedNewsFragment -> showBottomNavigation()
                R.id.searchNewsFragment -> showBottomNavigation()
                R.id.sourceScreenFragment -> hideBottomNavigation()
                R.id.settingsScreenFragment -> hideBottomNavigation()
                R.id.countryFragment -> hideBottomNavigation()
                R.id.categoryFragment -> hideBottomNavigation()
                R.id.sourceListScreenFragment -> hideBottomNavigation()
            }
        }
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
