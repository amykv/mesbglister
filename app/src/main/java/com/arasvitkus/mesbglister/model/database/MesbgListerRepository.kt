package com.arasvitkus.mesbglister.model.database

import androidx.annotation.WorkerThread
import com.arasvitkus.mesbglister.model.entities.MesbgLister

class MesbgListerRepository(private val mesbgListerDao: MesbgListerDao) {

    @WorkerThread
    suspend fun insertMesbgListerData(mesbgLister: MesbgLister){
        mesbgListerDao.insertMesbgListerDetails(mesbgLister)
    }
}