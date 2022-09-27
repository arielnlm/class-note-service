package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.remote

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.ClassResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassService {
    @GET("raspored/json.php")
    fun getAll(@Query("limit") limit: Int = 2000): Observable<List<ClassResponse>>
}