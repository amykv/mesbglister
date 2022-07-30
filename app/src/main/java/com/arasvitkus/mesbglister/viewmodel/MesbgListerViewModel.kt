package com.arasvitkus.mesbglister.viewmodel

import androidx.lifecycle.*
import com.arasvitkus.mesbglister.model.database.MesbgListerRepository
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 * You can also use a ViewModel to share data between fragments.
 * The ViewModel is part of the lifecycle library.
 *
 * @param repository - The repository class is
 */
//Pass repository into class. Inherit ViewModel.
//ViewModel is part of lifecycle library
class MesbgListerViewModel(private val repository: MesbgListerRepository) : ViewModel() {

    /**
     * Launching a new coroutine to insert the data in a non-blocking way.
     */
    //viewModelScope.launch was used originally. Replaced with CoroutineScope(Dispatchers.IO).launch {
    fun insert(armies: MesbgLister) = CoroutineScope(Dispatchers.IO).launch {
        //Call the repository function and pass the details
        repository.insertMesbgListerData(armies)
    }

    /** Using LiveData and caching what allDishes returns has several benefits:
     * Can put an observer on the data (instead of polling for changes) and only
     * update the UI when the data actually changes.
     * Repository is completely separated from the UI through the ViewModel.
     */
    //LiveData
    val allArmiesList: LiveData<List<MesbgLister>> = repository.allArmiesList.asLiveData()

    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    //Originally viewModelScope.launch, changed to CoroutineScope
    fun update(armies: MesbgLister) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateMesbgListerData(armies)
    }

    // Get the list of favorite armies that can populate in the UI.
    /** Using LiveData and caching what favoriteArmies returns has several benefits:
     * Can put an observer on the data (instead of polling for changes) and only
     * update the UI when the data actually changes.
     * Repository is completely separated from the UI through the ViewModel.
     */
    val favoriteArmies: LiveData<List<MesbgLister>> = repository.favoriteArmies.asLiveData()

    /**
     * Launching a new coroutine to delete the data in a non-blocking way.
     */
    fun delete(army: MesbgLister) = CoroutineScope(Dispatchers.IO).launch {
        //Call the repository function and pass the details.
        repository.deleteMesbgListerData(army)
    }

    /**
     * A function to get the filtered list of armies based on the dish type selection.
     *
     * @param value - army type selection
     */
    fun getFilteredList(value: String) : LiveData<List<MesbgLister>> = repository.filteredListArmies(value).asLiveData()
}

/**
 * To create the ViewModel, implement a ViewModelProvider.Factory that gets as a parameter the dependencies
 * needed to create FavDishViewModel: the MesbgListerRepository.
 * By using viewModels and ViewModelProvider.Factory then the framework will take care of the lifecycle of the ViewModel.
 * It will survive configuration changes and even if the Activity is recreated,
 * it will always get the right instance of the MesbgListerViewModel class.
 */
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
