package com.example.rsrafprojekat2mladen_jovanovic_3719rn.modules

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local.ClassDataBase
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.remote.ClassService
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.ClassRepository
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.ClassRepositoryImpl
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val classModule = module {

    viewModel { MainViewModel(classRepository = get(), noteRepository = get()) }

    single<ClassRepository> { ClassRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<ClassDataBase>().getClassDao() }

    single<ClassService> { create(retrofit = get()) }

}