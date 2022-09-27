package com.example.rsrafprojekat2mladen_jovanovic_3719rn.modules

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local.NoteDataBase
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.NoteRepository
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.NoteRepositoryImpl
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteModule = module {

    viewModel { MainViewModel(classRepository = get(), noteRepository =  get()) }

    single<NoteRepository> { NoteRepositoryImpl(localDataSource = get()) }

    single { get<NoteDataBase>().getNoteDao() }

}
