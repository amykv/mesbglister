package com.arasvitkus.mesbglister.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arasvitkus.mesbglister.model.entities.RandomArmy
import com.arasvitkus.mesbglister.model.network.RandomArmyApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

//Inherits from ViewModel
class RandomArmyViewModel : ViewModel() {

    private val randomArmyApiService = RandomArmyApiService()
    private val compositeDisposable = CompositeDisposable() //Hold onto multiple disposables

    val loadRandomArmy = MutableLiveData<Boolean>()
    val randomArmyResponse = MutableLiveData<RandomArmy.Quotes>()
    val randomArmyLoadingError = MutableLiveData<Boolean>()

    fun getRandomArmyFromAPI(){
        loadRandomArmy.value = true

        compositeDisposable.add(
            randomArmyApiService.getRandomArmy().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribeWith(object:
                DisposableSingleObserver<RandomArmy.Quotes>(){
                override fun onSuccess(value: RandomArmy.Quotes) {
                    loadRandomArmy.value = false
                    randomArmyResponse.value = value
                    randomArmyLoadingError.value = false
                }

                override fun onError(e: Throwable) {
                    loadRandomArmy.value = false
                    randomArmyLoadingError.value = false
                    e!!.printStackTrace()
                }

            })
        )
    }
}