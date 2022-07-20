package com.arasvitkus.mesbglister.viewmodel

import androidx.lifecycle.*
import com.arasvitkus.mesbglister.model.database.MesbgListerRepository
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Pass repository into class. Inherit ViewModel.
//ViewModel is part of lifecycle library
class MesbgListerViewModel(private val repository: MesbgListerRepository) : ViewModel() {

    //viewModelScope.launch was used originally. Replaced with CoroutineScope(Dispatchers.IO).launch {
    fun insert(armies: MesbgLister) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertMesbgListerData(armies)
    }

    //LiveData
    val allArmiesList: LiveData<List<MesbgLister>> = repository.allArmiesList.asLiveData()
}

//Class and function based from: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#9
class MesbgListerViewModelFactory(private val repository: MesbgListerRepository) : ViewModelProvider.Factory {

    //This function initially had ViewModel?, getting rid of ? got rid of error.
    //Found solution online: https://stackoverflow.com/questions/56195349/oncreateview-overrides-nothing
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        if (modelClass.isAssignableFrom(MesbgListerViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MesbgListerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
