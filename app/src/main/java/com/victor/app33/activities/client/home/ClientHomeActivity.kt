package com.victor.app33.activities.client.home


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.fragment.client.ClientCategoriesFragment
import com.victor.app33.fragment.client.ClientOrdersFragment
import com.victor.app33.fragment.client.ClientProfileFragment
import com.victor.app33.models.User
import com.victor.app33.routes.utils.SharedPref

class ClientHomeActivity : AppCompatActivity() {

    private val TAG = "ClientHomeActivity"
  //  var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null
    var bottonNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)
        sharedPref = SharedPref(this)
     //   buttonLogout = findViewById(R.id.btn_logout)
     //   buttonLogout?.setOnClickListener { logout()}
        openFragment(ClientCategoriesFragment())

        bottonNavigation = findViewById(R.id.bottom_navigation)
        bottonNavigation?.setOnItemSelectedListener {

            when(it.itemId){
                R.id.item_home -> {
                    openFragment(ClientCategoriesFragment())
                    true
                }
                R.id.item_orders -> {
                    openFragment(ClientOrdersFragment())
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

    private fun getUserFromSession() {

        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: $user")
        }

    }
}