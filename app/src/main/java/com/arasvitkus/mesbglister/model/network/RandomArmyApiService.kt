package com.arasvitkus.mesbglister.model.network

import com.arasvitkus.mesbglister.model.entities.RandomArmy
import com.arasvitkus.mesbglister.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomArmyApiService {

    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build().create(RandomArmyAPI::class.java)

    fun getRandomArmy(): Single<RandomArmy.Quotes>{
        return api.getRandomArmy(Constants.API_KEY_VALUE)
    }
}