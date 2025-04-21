package com.victor.app33.activities
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.victor.app33.R
import com.victor.app33.api.RetrofitCliente
import com.victor.app33.models.ResponseHttp
import com.victor.app33.routes.UsersRoutes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Actualizar_contrasenia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_contrasenia)


    }


}
