package com.victor.app33.api

import com.victor.app33.routes.UsersRoutes

class ApiRoutes {

    val API_URL = "http://192.168.1.233:3000/api/"
    val retrofit = RetrofitCliente()

    fun getUsersRoutes(): UsersRoutes {
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

}