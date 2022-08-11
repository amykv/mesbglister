package com.arasvitkus.mesbglister.application

import android.app.Application
import com.arasvitkus.mesbglister.model.database.MesbgListerRepository
import com.arasvitkus.mesbglister.model.database.MesbgListerRoomDatabase

//Privacy Policy - https://avitkdev.blogspot.com/2022/08/privacy-policy-for-mesbglister.html
//Define variable scope throughout application. Setup database and repository
class MesbgListerApplication : Application() {

    /**Using by lazy so the database and the repository are only created when they're needed
     * rather than when the application starts.
     */
    /**
     * The "lazy" keyword used for creating a new instance that uses the specified initialization function
     * and the default thread-safety mode [LazyThreadSafetyMode.SYNCHRONIZED].
     *
     * If the initialization of a value throws an exception, it will attempt to reinitialize the value at next access.
     *
     * Note that the returned instance uses itself to synchronize on. Do not synchronize from external code on
     * the returned instance as it may cause accidental deadlock. Also this behavior can be changed in the future.
     */
    private val database by lazy { MesbgListerRoomDatabase.getDatabase((this@MesbgListerApplication))}

    // A variable for repository
    val repository by lazy { MesbgListerRepository(database.mesbgListerDao())}
}