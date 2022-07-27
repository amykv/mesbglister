package com.arasvitkus.mesbglister.model.database

import androidx.annotation.WorkerThread
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.flow.Flow

class MesbgListerRepository(private val mesbgListerDao: MesbgListerDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesbgListerData(mesbgLister: MesbgLister){
        mesbgListerDao.insertMesbgListerDetails(mesbgLister)
    }

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
}