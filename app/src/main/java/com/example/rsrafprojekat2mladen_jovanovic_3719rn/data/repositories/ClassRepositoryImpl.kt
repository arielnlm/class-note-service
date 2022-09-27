package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local.ClassDao
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.remote.ClassService
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.ClassEntity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class ClassRepositoryImpl(
    private val localDataSource: ClassDao,
    private val remoteDataSource: ClassService
) : ClassRepository {

    // uzima sa neka i upisuju u bazu
    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll() // doOnNext presrece next, na svaki emit upisujemo u bazu
            .doOnNext {
                Timber.e("Upis u bazu")
                val entities = it.map {
                    ClassEntity(
                        it.id,
                        it.subject,
                        it.type,
                        it.teacher,
                        it.groups,
                        it.day,
                        it.time,
                        it.classroom
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
        // Kada zelimo sami da kontrolisemo sta se desava sa greskom, umesto da je samo prepustimo
        // error handleru, mozemo iskoristiti operator onErrorReturn, tada sami na osnovu greske
        // odlucujemo koju vrednost cemo da vratimo. Ta vrednost mora biti u skladu sa povratnom
        // vrednoscu lanca.
        // Kada se iskoristi onErrorReturn onError lambda u viewmodelu nece biti izvrsena,
        // nego ce umesdto nje biti izvsena onNext koja ce kao parametar primiti vrednost koja
        // je vracena iz onErrorReturn
        // Obicno se koristi kada je potrebno proveriti koji kod greske je vratio server.
//            .onErrorReturn {
//                when(it) {
//                    is HttpException -> {
//                        when(it.code()) {
//                            in 400..499 -> {
//
//                            }
//                        }
//                    }
//                }
//                Timber.e("ON ERROR RETURN")
//            }
    }

    override fun getAll(): Observable<List<Class>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Class(it.id,
                        it.subject,
                        it.type,
                        it.teacher,
                        it.groups,
                        it.day,
                        it.time,
                        it.classroom)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Class>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Class(it.id,
                        it.subject,
                        it.type,
                        it.teacher,
                        it.groups,
                        it.day,
                        it.time,
                        it.classroom)
                }
            }
    }

    override fun getByDayClassName(name: String, day:String, className: String): Observable<List<Class>> {
        return localDataSource
            .getByDayClassName(name, day, className)
            .map {
                it.map {
                    Class(it.id,
                        it.subject,
                        it.type,
                        it.teacher,
                        it.groups,
                        it.day,
                        it.time,
                        it.classroom)
                }
            }
    }

    override fun insert(myClass: Class): Completable {
        val classEntity = ClassEntity(
            myClass.id,
            myClass.subject,
            myClass.type,
            myClass.teacher,
            myClass.groups,
            myClass.day,
            myClass.time,
            myClass.classroom
        )
        return localDataSource
            .insert(classEntity)
    }
}