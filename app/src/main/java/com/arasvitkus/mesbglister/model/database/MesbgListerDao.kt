package com.arasvitkus.mesbglister.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.arasvitkus.mesbglister.model.entities.MesbgLister

@Dao
interface MesbgListerDao {

    //Had to remove suspend prefix from function to get to work
    //Potential solution due to suspend: https://youtrack.jetbrains.com/issue/KT-49761
    @Insert
    fun insertMesbgListerDetails(mesbgLister: MesbgLister)
}