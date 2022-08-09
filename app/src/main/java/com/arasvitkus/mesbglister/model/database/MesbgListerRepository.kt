package com.arasvitkus.mesbglister.model.database

import androidx.annotation.WorkerThread
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.flow.Flow

/**
 * A Repository manages queries and allows use of multiple backends.
 *
 * The DAO is passed into the repository constructor as opposed to the whole database.
 * This is because it only needs access to the DAO, since the DAO contains all the read/write methods for the database.
 * There's no need to expose the entire database to the repository.
 *
 * @param mesbgListerDao - Pass the MesbgListerDao as the parameter.
 */
class MesbgListerRepository(private val mesbgListerDao: MesbgListerDao) {

    /**
     * By default Room runs suspend queries off the main thread, therefore, don't need to
     * implement anything else to ensure it's not doing long running database work
     * off the main thread.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesbgListerData(mesbgLister: MesbgLister){
        mesbgListerDao.insertMesbgListerDetails(mesbgLister)
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //https://developer.android.com/kotlin/flow
    //In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
    // For example, you can use a flow to receive live updates from a database.
    val allArmiesList: Flow<List<MesbgLister>> = mesbgListerDao.getAllArmiesList()

    @WorkerThread
    suspend fun updateMesbgListerData(mesbgLister: MesbgLister){
        mesbgListerDao.updateFavoriteArmyDetails(mesbgLister)
    }

    val favoriteArmies: Flow<List<MesbgLister>> = mesbgListerDao.getFavoriteArmiesList()

    @WorkerThread
    suspend fun deleteMesbgListerData(mesbgLister: MesbgLister){
        mesbgListerDao.deleteMesbgListerDetails(mesbgLister)
    }

    //Function to get the filtered list of armies.
    fun filteredListArmies(value: String) : Flow<List<MesbgLister>> = mesbgListerDao.getFilteredArmiesList(value)
}