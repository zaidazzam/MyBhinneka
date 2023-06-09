package com.d3if3055.assesment2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.d3if3055.assesment2.model.Suku
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SukuApi {
    @GET("suku.json")
    suspend fun getSuku(): List<Suku>
}


object ServiceAPI {
    private const val BASE_URL_SUKU = "https://raw.githubusercontent.com/" +
            "zaidazzam/MyBhinneka/SukuAPI/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofitSuku = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL_SUKU)
        .build()

    //Retrofit Create


    val sukuService: SukuApi by lazy {
        retrofitSuku.create(SukuApi::class.java)
    }

    //Function


    fun getSukuUrl(imageId: String): String {
        return "$BASE_URL_SUKU$imageId.jpg"
    }
}


enum class ApiStatus { LOADING, SUCCES,FAILED}
