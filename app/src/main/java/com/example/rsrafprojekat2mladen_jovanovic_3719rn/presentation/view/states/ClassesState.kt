package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class

// stanja vezana za UI
sealed class ClassesState {
    object Loading: ClassesState()
    object DataFetched: ClassesState()
    data class Success(val classes: List<Class>): ClassesState()
    data class Error(val message: String): ClassesState()

}