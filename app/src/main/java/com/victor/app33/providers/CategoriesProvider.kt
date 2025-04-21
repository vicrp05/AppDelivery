package com.victor.app33.providers

import com.victor.app33.api.ApiRoutes
import com.victor.app33.models.Category
import com.victor.app33.models.ResponseHttp
import com.victor.app33.models.User
import com.victor.app33.routes.CategoriesRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class CategoriesProvider (val token:String){
    private var categoriesRoutes: CategoriesRoutes? = null

    init{
        val api = ApiRoutes()
        categoriesRoutes = api.getCategoriesRoutes(token)

    }

    fun getAll(): Call<ArrayList<Category>>? {
        return categoriesRoutes?.getAll(token)

    }
    fun create(file: File, category: Category): Call<ResponseHttp>?{
        val reqFile = RequestBody.create(MediaType.parse("image/*"),file)
        val image = MultipartBody.Part.createFormData("image",file.name, reqFile)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), category.toJson())
        return categoriesRoutes?.create(image,requestBody, token)

    }
}