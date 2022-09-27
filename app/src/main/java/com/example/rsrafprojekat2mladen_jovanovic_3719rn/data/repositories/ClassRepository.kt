package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Resource
import io.reactivex.Completable
import io.reactivex.Observable

interface ClassRepository {

    fun fetchAll(): Observable<Resource<Unit>> // uzima sa neta
    fun getAll(): Observable<List<Class>> // uzima iz lokalne baze
    fun getAllByName(name: String): Observable<List<Class>>
    fun getByDayClassName(name: String, day:String, className: String): Observable<List<Class>>
    fun insert(myClass: Class): Completable

}