package com.victor.app33.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.activities.client.home.ClientHomeActivity
import com.victor.app33.models.ResponseHttp
import com.victor.app33.models.User
import com.victor.app33.providers.UsersProvider
import com.victor.app33.routes.utils.SharedPref
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SaveImageActivity : AppCompatActivity() {

    val TAG = "SaveImageActivity"

    private var circleImageUser: CircleImageView? = null
    private var buttonNext: Button? = null
    private var buttonConfirm: Button? = null

    private var imageFile: File? = null

    var usersProvider: UsersProvider?= null
    var user: User? = null
    var sharedPref: SharedPref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_image)

        sharedPref = SharedPref(this)

        getUserFromSession()
        usersProvider = UsersProvider(user?.sessionToken)


        // 🔹 Corrección: Asignar los IDs correctamente
        circleImageUser = findViewById(R.id.circleimage_user)
        buttonNext = findViewById(R.id.btn_next) // ⚠️ Asegúrate de que exista este botón en tu XML
        buttonConfirm = findViewById(R.id.btn_confirm)

        // 🔹 Corrección: Evento clic en la imagen
        circleImageUser?.setOnClickListener { selectImage() }
        buttonNext?.setOnClickListener {goToClientHome()}
        buttonConfirm?.setOnClickListener {saveImage()}
    }

    private fun saveImage() {

        if(imageFile != null && user != null){
            usersProvider?.update(imageFile!!, user!! )?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "RESPONSE: $response ")
                    Log.d(TAG, "BODY: ${response.body()} ")
                    saveUserInSession(response.body()?.data.toString())
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    Toast.makeText(this@SaveImageActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        else {

            Toast.makeText(this, "La imagen no puede ser nula ni tampoco los datos de session del usuario", Toast.LENGTH_LONG).show()
        }

    }

    private fun saveUserInSession(data: String) {
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref?.save("user", user)
        goToClientHome()
    }

    private fun goToClientHome() {
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
        startActivity(i)
    }

    private fun getUserFromSession() {
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val data = result.data

            if (result.resultCode == RESULT_OK && data != null) { // ✅ Corrección aquí
                val fileUri = data.data
                imageFile = File(fileUri?.path ?: "")
                circleImageUser?.setImageURI(fileUri)
            } else if (result.resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Tarea cancelada", Toast.LENGTH_LONG).show()
            }
        }

    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }
}