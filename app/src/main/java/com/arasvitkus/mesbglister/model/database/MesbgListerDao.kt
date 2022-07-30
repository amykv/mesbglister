package com.arasvitkus.mesbglister.model.database

import androidx.room.*
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import kotlinx.coroutines.flow.Flow

@Dao
interface MesbgListerDao {

    /**
     * All queries must be executed on a separate thread. They cannot be executed from Main Thread or it will cause an crash.
     *
     * Room has Kotlin coroutines support.
     * This allows queries to be annotated with the suspend modifier and then called from a coroutine
     * or from another suspension function.
     */

    // Function to instert favorite list details to the local database using Room.
    //Had to remove suspend prefix from function to get to work
    //Potential solution due to suspend: https://youtrack.jetbrains.com/issue/KT-49761
    @Insert
    fun insertMesbgListerDetails(mesbgLister: MesbgLister)

    /**
     * When data changes, usually want to take some action, such as displaying the updated data in the UI.
     * This means there has to be a way to observe the data so when it changes, to be able to react.
     *
     * To observe data changes we will use Flow from kotlinx-coroutines.
     * Use a return value of type Flow in method description,
     * and Room generates all necessary code to update the Flow when the database is updated.
     *
     * A Flow is an async sequence of values
     * Flow produces values one at a time (instead of all at once) that can generate values from async operations
     * like network requests, database calls, or other async code.
     * It supports coroutines throughout its API, allows ability to transform a flow using coroutines as well.
     */

    @Query("SELECT * FROM army_list_table ORDER BY ID")
    fun getAllArmiesList(): Flow<List<MesbgLister>>

    /**
     * A function to update favorite army details to the local database using Room.
     *
     * @param mesbgLister - will pass the entity class that  has been created with updated details along with "id".
     */
    @Update
    fun updateFavoriteArmyDetails(mesbgLister: MesbgLister) //Coroutine function

    /**
     * SQLite does not have a boolean data type. Room maps it to an INTEGER column, mapping true to 1 and false to 0.
     */
    @Query("SELECT * FROM army_list_table WHERE favorite_army = 1") //true = 1
    fun getFavoriteArmiesList() : Flow<List<MesbgLister>>

    /**
     * A function to delete favorite army details from the local database using Room.
     *
     * @param mesbgLister - This will pass the entity class with details that user wants to delete.
     */
    @Delete
    fun deleteMesbgListerDetails(mesbgLister: MesbgLister)

    /**
     * A function to get the list of armies based on the army type from the database.
     *
     * @param filterType - armyType(type)
     */
    @Query("SELECT * FROM army_list_table WHERE type = :filterType")
    fun getFilteredArmiesList(filterType: String): Flow<List<MesbgLister>>
}