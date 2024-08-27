package com.example.winit_kotlin.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.databinding.ActivityHomeBinding
import com.example.winit_kotlin.fragments.BlockedCustomerFragment
import com.example.winit_kotlin.fragments.DashboardFragment
import com.example.winit_kotlin.fragments.JourneyPlanFragment
import com.example.winit_kotlin.fragments.MyCustomerFragment
import com.example.winit_kotlin.fragments.NextDayJourneyPlanFragment
import com.example.winit_kotlin.interfaces.OnDialogListeners
import com.example.winit_kotlin.menu.DashBoardOptionsCustomAdapter
import com.example.winit_kotlin.menu.MenuClass
import com.example.winit_kotlin.menu.MenuDO
import com.example.winit_kotlin.utils.showCustomDialog
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(),OnDialogListeners {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var preference: Preference
    private lateinit var adapter: DashBoardOptionsCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_home)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        preference = Preference(this)
        val toggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.nav_open,R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.toolbar.setTitle(getString(R.string.home))
//        binding.navView.setNavigationItemSelectedListener(this)
        fragmentManager = supportFragmentManager
        // Show DashboardFragment by default
        if (savedInstanceState == null) {
            openDashboardFragment("Dashboard")
        }
        adapter = DashBoardOptionsCustomAdapter(MenuClass(this).loadMenu(preference.getIntFromPreference(Preference.USER_TYPE_NEW,AppConstants.USER_VAN_SALES),false)){ menuDO ->
            TopBarMenuClick()
            moveToNextPage(menuDO)
        }
        binding.llExpandlistMenu.setAdapter(adapter)


        //Search view functionality
        binding.llsearch.apply {
            searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Show or hide the cancel button based on text input
                    if (newText.isNullOrEmpty()) {

                    } else {

                    }
                    return true
                }
            })

            // Set up a focus change listener to handle focus events
            searchview.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    llcancel.visibility = TextView.VISIBLE
                } else {
                    llcancel.visibility = TextView.GONE
                }
            }

            // Handle cancel button click
            llcancel.setOnClickListener {
                searchview.setQuery("", false)
                searchview.clearFocus()
                llcancel.visibility = TextView.GONE
            }
        }


    }


    fun TopBarMenuClick(){
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val dashboardFragment = fragmentManager.findFragmentByTag(DashboardFragment::class.java.simpleName)
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else if (fragmentManager.backStackEntryCount > 0 && dashboardFragment == null) {
            fragmentManager.popBackStack()
        } else{
            showCustomDialog(this,preference,getString(R.string.alert),getString(R.string.do_you_want_to_logout),getString(R.string.OK),getString(R.string.No),"Logout",this)
        }
//            super.onBackPressedDispatcher.onBackPressed()
    }

    private fun openFragment(fragment: Fragment,titileName :String){
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
            .addToBackStack(null)  // Add to backstack so that back button works properly
            .commit()

    }
    fun openFragmentFromMenu(fragmentClass: Class<out Fragment>,titileName :String) {
        val fragmentTag = fragmentClass.simpleName
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Check if the fragment is already in the back stack
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)
        clearBackStack(fragmentManager)
        if (existingFragment != null) {
            // If the fragment is in the back stack, remove it first
            fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // Replace the fragment with the new instance
        fragmentTransaction.replace(R.id.fragment_container, fragmentClass.newInstance(), fragmentTag)
        fragmentTransaction.addToBackStack(fragmentTag)

        fragmentTransaction.commit()
        binding.toolbar.setTitle(titileName)
    }
    fun clearBackStack(fragmentManager: FragmentManager) {
        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }
    }
     fun openFragment(fragmentClass: Class<out Fragment>,titileName :String) {
        val fragmentTag = fragmentClass.simpleName
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Check if the fragment is already in the back stack
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)

        if (existingFragment != null) {
            // If the fragment is in the back stack, remove it first
            fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // Replace the fragment with the new instance
        fragmentTransaction.replace(R.id.fragment_container, fragmentClass.newInstance(), fragmentTag)
        fragmentTransaction.addToBackStack(fragmentTag)

        fragmentTransaction.commit()
         binding.toolbar.setTitle(titileName)
    }
    private fun openDashboardFragment(titileName :String) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DashboardFragment())
            .commit()  // No addToBackStack here, so it's the root fragment
    }


    fun moveToNextPage(menuDO: MenuDO){

        if (menuDO.objMenu != null) {
            when (menuDO.objMenu) {
                MenuClass.MENUS.MENU_JOURNEY_PLAN -> {
                    openFragmentFromMenu(JourneyPlanFragment::class.java,getString(R.string.journey_plan))
                }

                MenuClass.MENUS.MENU_NEXTDAY_JOURNEY_PLAN -> {
                    binding.toolbar.setTitle(getString(R.string.journey_plan))
                    openFragmentFromMenu(NextDayJourneyPlanFragment::class.java,getString(R.string.journey_plan))
                }

                MenuClass.MENUS.MENU_MY_CUSTOMER -> {

                    openFragmentFromMenu(MyCustomerFragment::class.java,getString(R.string.mycustomer))
                }

                MenuClass.MENUS.MENU_BLOCKED_CUSTOMER -> {

                    openFragmentFromMenu(BlockedCustomerFragment::class.java,getString(R.string.my_Blocked_Customers))
                }

                MenuClass.MENUS.MENU_LOAD_MANAGEMENT -> {


                }

                MenuClass.MENUS.MENU_EXECUTION_SUMMARY -> {

                }

                MenuClass.MENUS.MENU_ADD_NEW_CUSTOMER -> {

                }

                MenuClass.MENUS.MENU_OTHERS -> {

                }

                MenuClass.MENUS.MENU_LOGOUT -> {
                    showCustomDialog(this,preference,getString(R.string.alert),getString(R.string.do_you_want_to_logout),getString(R.string.OK),getString(R.string.No),"Logout",this)
                }

                MenuClass.MENUS.MENU_CUSTOMER_DASHBOARD -> {

                }

                MenuClass.MENUS.MENU_SETTINGS -> {

                }

                MenuClass.MENUS.MENU_ABOUT_APPLICATION -> {

                }

                MenuClass.MENUS.MENU_VAN_STOCK -> {

                }

                MenuClass.MENUS.MENU_PAYMENT_SUMMMARY -> {

                }

                MenuClass.MENUS.MENU_ORDER_SUMMMARY -> {

                }

                MenuClass.MENUS.MENU_CHECK_OUT -> {

                }

                MenuClass.MENUS.MENU_ACTIVE_PROMOTIONS -> {

                }

                MenuClass.MENUS.MENU_ACTIVE_SP -> {

                }

                MenuClass.MENUS.MENU_SURVEY -> {

                }

                MenuClass.MENUS.MENU_ENDORSEMENT -> {

                }

                MenuClass.MENUS.MENU_LOG_REPORT -> {

                }

                MenuClass.MENUS.MENU_RETURN_SUMMARY -> {

                }

                MenuClass.MENUS.MENU_DAMAGE_RETURNS -> {

                }

                MenuClass.MENUS.MENU_EXPIRY_RETURNS -> {

                }

                MenuClass.MENUS.MENU_UNLOAD -> {

                }

                MenuClass.MENUS.MENU_FOOTER -> {

                }

                MenuClass.MENUS.MENU_DELIVERY_DOCKET -> {

                }

                null -> Toast.makeText(this, "Error.......!😍", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onButtonYesClick(from: String) {
        if (from.equals("Logout",ignoreCase = true))
            super.onBackPressedDispatcher.onBackPressed()
    }

    override fun onButtonYesClick(from: String, any: Any) {

    }

    override fun onButtonNoClick(from: String) {

    }

    override fun onButtonNoClick(from: String, any: Any) {

    }
}