package com.victor.app33.activities.client


import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.activities.MainActivity
import com.victor.app33.models.User
import com.victor.app33.routes.utils.SharedPref

class ClientHomeActivity : AppCompatActivity() {

    private val TAG = "ClientHomeActivity"

    var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)

        sharedPref = SharedPref(this)
        buttonLogout = findViewById(R.id.btn_logout)
        buttonLogout?.setOnClickListener { logout() }

        getUserFromSession()
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