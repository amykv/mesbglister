package com.arasvitkus.mesbglister.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.arasvitkus.mesbglister.model.entities.MesbgLister

@Dao
interface MesbgListerDao {

    @Insert
    suspend fun insertMesbgListerDetails(mesbgLister: MesbgLister)
}