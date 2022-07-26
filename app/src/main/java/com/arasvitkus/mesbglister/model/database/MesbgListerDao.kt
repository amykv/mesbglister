package com.arasvitkus.mesbglister.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.flow.Flow

@Dao
interface MesbgListerDao {

    //Had to remove suspend prefix from function to get to work
    //Potential solution due to suspend: https://youtrack.jetbrains.com/issue/KT-49761
    @Insert
    fun insertMesbgListerDetails(mesbgLister: MesbgLister)

    @Query("SELECT * FROM army_list_table ORDER BY ID")
    fun getAllArmiesList(): Flow<List<MesbgLister>>

    @Update
    fun updateFavoriteArmyDetails(mesbgLister: MesbgLister) //Coroutine function

    @Query("SELECT * FROM army_list_table WHERE favorite_army = 1") //true = 1
    fun getFavoriteArmiesList() : Flow<List<MesbgLister>>
}