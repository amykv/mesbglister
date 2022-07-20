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
}