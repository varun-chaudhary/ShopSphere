package com.example.shopsphere.data.remote.retrofit

import com.example.shopsphere.model.LoginResponse
import com.example.shopsphere.model.ProductResponseItem
import com.example.shopsphere.model.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : UserResponse

    @GET("products")
    suspend fun getAllProduct(
        @Query("limit") limit: Int
    ): List<ProductResponseItem>

    @GET("products")
    suspend fun sortProduct(
        @Query("sort") sort: String
    ): List<ProductResponseItem>

    @GET("products/category/{category}")
    suspend fun getProductByCategory(
        @Path("category")
        category: String,
        @Query("limit")
        limit: Int
    ): List<ProductResponseItem>

    @GET("products/{id}")
    suspend fun getSingleProduct(
        @Path("id")
        id: Int
    ): ProductResponseItem
}