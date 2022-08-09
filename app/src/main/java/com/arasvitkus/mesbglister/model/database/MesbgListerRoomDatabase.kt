package com.arasvitkus.mesbglister.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arasvitkus.mesbglister.model.entities.MesbgLister

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * Contains the database and serves as the main access point for the underlying connection in MESBGListers app data.
 */
@Database(entities = [MesbgLister::class], version = 1, exportSchema = false)
abstract class MesbgListerRoomDatabase : RoomDatabase() {

    abstract fun mesbgListerDao(): MesbgListerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MesbgListerRoomDatabase? = null

        //This function taken from: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#7
        fun getDatabase(context: Context): MesbgListerRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MesbgListerRoomDatabase::class.java,
                    "mesbglister_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


    }
}