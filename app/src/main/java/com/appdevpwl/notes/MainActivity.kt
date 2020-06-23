package com.appdevpwl.notes

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat.getActionView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() , OnNavigationItemSelectedListener{
    private lateinit var navView: NavigationView
    private lateinit var homeFragment:HomeFragment
    private lateinit var binFragment: BinFragment
    private lateinit var aboutFragment:AboutFragment
    private lateinit var settingsFragment:SettingsFragment
    private lateinit var noteCount: TextView
    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(applicationContext)
        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        toolbar_title.setText(R.string.toolbar_name_home)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

        val drawerToggle : ActionBarDrawerToggle = object :ActionBarDrawerToggle(this,drawerLayout, main_toolbar, 0, 0 ){

        }

      noteCount = getActionView(navView.menu.findItem(R.id.nav_my_notes)) as TextView
    

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


//        ****set start fragment
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        toolbar_title.setText(R.string.toolbar_title_home)

        }

    override fun onResume() {
        super.onResume()
        initializeCountNavDrawer()

    }


    private fun initializeCountNavDrawer() {
        noteCount.text = dbHelper.countAllNotes().toString()
        noteCount.gravity=(Gravity.CENTER_VERTICAL)
        noteCount.typeface= Typeface.DEFAULT_BOLD

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.nav_my_notes ->{
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                toolbar_title.setText(R.string.toolbar_title_home)

            }
//            R.id.nav_bin_notes->{
//                binFragment = BinFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragment_container,binFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//                toolbar_title.setText(R.string.toolbar_title_bin)
//
//            }
            R.id.nav_settings->{
                settingsFragment=SettingsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,settingsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                toolbar_title.setText(R.string.toolbar_title_settings)
            }
            R.id.nav_about->{
                aboutFragment=AboutFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,aboutFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                toolbar_title.setText(R.string.toolbar_title_about)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }


}


