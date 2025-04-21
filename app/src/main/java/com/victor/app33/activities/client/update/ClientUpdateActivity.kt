package com.victor.app33.activities.client.update

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.victor.app33.R
import com.victor.app33.models.ResponseHttp
import com.victor.app33.models.User
import com.victor.app33.providers.UsersProvider
import com.victor.app33.routes.utils.SharedPref
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ClientUpdateActivity : AppCompatActivity() {

    val TAG = "ClientUpdateActivity"
    var circleImageUser: CircleImageView? = null
    var editTextName: EditText? = null
    var editTextLastname: EditText? = null
    var editTextPhone: EditText? = null
    var buttonUpdate: Button? = null

    var sharedPref: SharedPref? = null
    var user: User? = null

    private var imageFile: File? = null
    var usersProvider: UsersProvider? = null
    var toolbar: Toolbar? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_update)

    sharedPref = SharedPref(this)

    toolbar = findViewById(R.id.toolbar)
    toolbar?.title = "Editar Perfil"
    toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    circleImageUser = findViewById(R.id.circleimage_user)
    editTextName = findViewById(R.id.edittext_name)
    editTextLastname  = findViewById(R.id.edittext_lastname)
    editTextPhone = findViewById(R.id.edittext_phone)
    buttonUpdate = findViewById(R.id.btn_update)

    getUserFromSession()
    usersProvider = UsersProvider(user?.sessionToken)

    editTextName?.setText(user?.name)
    editTextLastname?.setText(user?.lastname)
    editTextPhone?.setText(user?.phone)


    if(!user?.image.isNullOrBlank()){
        Glide.with(this).load(user?.image).into(circleImageUser!!)
    }

    circleImageUser?.setOnClickListener{ selectImage() }
    buttonUpdate?.setOnClickListener{updateData()}




        }

    private fun updateData() {

        val name = editTextName?.text.toString()
        val lastname = editTextLastname?.text.toString()
        val phone = editTextPhone?.text.toString()

        user?.name = name
        user?.lastname = lastname
        user?.phone = phone

        if (imageFile != null){

            //envio datos de cesion del usuario
            usersProvider?.update(imageFile!!, user!! )?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "RESPONSE: $response ")
                    Log.d(TAG, "BODY: ${response.body()} ")

                    Toast.makeText(this@ClientUpdateActivity, response.body()?.message, Toast.LENGTH_LONG).show()

                    if (response.body()?.isSuccess == true){
                        saveUserInSession(response.body()?.data.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    Toast.makeText(this@ClientUpdateActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })


        }

        else{
            //envio datos de cesion del usuario
            usersProvider?.updateWithoutImage(user!! )?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "RESPONSE: $response ")
                    Log.d(TAG, "BODY: ${response.body()} ")

                    Toast.makeText(this@ClientUpdateActivity, response.body()?.message, Toast.LENGTH_LONG).show()

                    if (response.body()?.isSuccess == true){
                        saveUserInSession(response.body()?.data.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    Toast.makeText(this@ClientUpdateActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }





    }

    private fun saveUserInSession(data: String) {
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref?.save("user", user)

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
