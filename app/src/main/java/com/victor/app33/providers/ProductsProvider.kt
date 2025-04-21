package com.victor.app33.providers

import com.victor.app33.api.ApiRoutes
import com.victor.app33.models.Category
import com.victor.app33.models.Product
import com.victor.app33.models.ResponseHttp
import com.victor.app33.models.User
import com.victor.app33.routes.CategoriesRoutes
import com.victor.app33.routes.ProductsRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class ProductsProvider(val token: String) {

    private var productsRoutes: ProductsRoutes? = null

    init {
        val api = ApiRoutes()
        productsRoutes = api.getProductsRoutes(token)
    }

     fun findByCategory(idCategory: String): Call<ArrayList<Product>>? {
         return productsRoutes?.findByCategory(idCategory, token)
     }

    fun create(files: List<File>, product: Product): Call<ResponseHttp>? {

        val images = arrayOfNulls<MultipartBody.Part>(files.size)

        for (i in 0 until files.size) {
            val reqFile = RequestBody.create(MediaType.parse("image/*"), files[i])
            images[i] = MultipartBody.Part.createFormData("image", files[i].name, reqFile)
        }

        val requestBody = RequestBody.create(MediaType.parse("text/plain"), product.toJson())
        return productsRoutes?.create(images, requestBody, token)
    }
}
