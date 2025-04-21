package com.victor.app33.api

import com.victor.app33.routes.CategoriesRoutes
import com.victor.app33.routes.ProductsRoutes
import com.victor.app33.routes.UsersRoutes

class ApiRoutes {

    // La URL de la API (Asegúrate de que sea la correcta para tu servidor)
    val API_URL = "http://192.168.1.233:3000/api/"
    // Instancia del cliente Retrofit
    private val retrofit = RetrofitCliente()

    // Método para obtener las rutas de usuarios
    fun getUsersRoutes(): UsersRoutes {
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

    fun getUsersRoutesWithToken(token: String): UsersRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }

    fun getCategoriesRoutes(token: String): CategoriesRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(CategoriesRoutes::class.java)
    }
    fun getProductsRoutes(token: String): ProductsRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(ProductsRoutes::class.java)
    }
}

