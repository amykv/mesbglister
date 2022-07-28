package com.arasvitkus.mesbglister.model.network

import com.arasvitkus.mesbglister.model.entities.RandomArmy
import com.arasvitkus.mesbglister.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomArmyAPI {

    @GET(Constants.API_ENDPOINT)
    fun getRandomArmy(
        @Query(Constants.API_KEY) apiKey: String
    ): Single<RandomArmy.Quotes>
}