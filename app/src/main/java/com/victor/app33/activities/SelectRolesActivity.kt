package com.victor.app33.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.adapters.RolesAdapter
import com.victor.app33.models.User
import com.victor.app33.routes.utils.SharedPref

class SelectRolesActivity : AppCompatActivity() {

    var recycleViewRoles: RecyclerView? = null
    var user: User? = null
    var adapter: RolesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_roles)

        recycleViewRoles = findViewById(R.id.recycleview_roles)
        recycleViewRoles?.layoutManager = LinearLayoutManager(this)

        getUserFromSession()
        adapter = RolesAdapter(this, user?.roles!!)
        recycleViewRoles?.adapter = adapter
    }

    private fun getUserFromSession() {

        val sharedPref = SharedPref(this)
        val gson = Gson()

        if (!sharedPref.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            user = gson.fromJson(sharedPref.getData("user"), User::class.java)

        }

    }
}
