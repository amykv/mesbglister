package com.arasvitkus.mesbglister.application

import android.app.Application
import com.arasvitkus.mesbglister.model.database.MesbgListerRepository
import com.arasvitkus.mesbglister.model.database.MesbgListerRoomDatabase

//Define variable scope throughout application. Setup database and repository
class MesbgListerApplication : Application() {

    private val database by lazy { MesbgListerRoomDatabase.getDatabase((this@MesbgListerApplication))}

    val repository by lazy { MesbgListerRepository(database.mesbgListerDao())}
}