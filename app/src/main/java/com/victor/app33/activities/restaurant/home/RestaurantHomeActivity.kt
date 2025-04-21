package com.victor.app33.activities.restaurant.home

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.activities.MainActivity
import com.victor.app33.fragment.client.ClientCategoriesFragment
import com.victor.app33.fragment.client.ClientOrdersFragment
import com.victor.app33.fragment.client.ClientProfileFragment
import com.victor.app33.fragment.restaurant.RestaurantCategoryFragment
import com.victor.app33.fragment.restaurant.RestaurantOrdersFragment
import com.victor.app33.fragment.restaurant.RestaurantProductFragment
import com.victor.app33.models.User
import com.victor.app33.routes.utils.SharedPref

class RestaurantHomeActivity : AppCompatActivity() {

    private val TAG = "RestaurantHomeActivity"
    //  var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null
    var bottonNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)
        sharedPref = SharedPref(this)
        //   buttonLogout = findViewById(R.id.btn_logout)
        //   buttonLogout?.setOnClickListener { logout()}
        openFragment(RestaurantOrdersFragment())

        bottonNavigation = findViewById(R.id.bottom_navigation)
        bottonNavigation?.setOnItemSelectedListener {

            when(it.itemId){
                R.id.item_home -> {
                    openFragment(RestaurantOrdersFragment())
                    true
                }
                R.id.item_category -> {
                    openFragment(RestaurantCategoryFragment())
                    true
                }
                R.id.item_product -> {
                    openFragment(RestaurantProductFragment())
                    true
                }
                R.id.item_profile -> {
                    openFragment(ClientProfileFragment())
                    true
                }
                else -> false
            }
        }

        getUserFromSession()
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
    private fun logout() {
        sharedPref?.remove("user")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()


    }
    private fun getUserFromSession() {


        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: $user")
        }

    }
}