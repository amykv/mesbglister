package com.arasvitkus.mesbglister.view.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //val navView: BottomNavigationView = mBinding.navView

        mNavController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_all_armies, R.id.navigation_favorite_armies
            )
        )
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        mBinding.navView.setupWithNavController(mNavController)
    }

    //Makes sure user can navigate back - from details to all armies fragment for example
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, null)
    }

    /**
     * A function to hide the BottomNavigationView with animation.
     */
    fun hideBottomNavigationView(){
        mBinding.navView.clearAnimation()
        // Animation to Navigate and translate element by height over duration of 300ms
        mBinding.navView.animate().translationY(mBinding.navView.height.toFloat()).duration = 300
    }

    /**
     * A function to show the BottomNavigationView with Animation.
     */
    fun showBottomNavigationView(){
        mBinding.navView.clearAnimation()
        //Position back
        mBinding.navView.animate().translationY(0f).duration = 300
    }
}