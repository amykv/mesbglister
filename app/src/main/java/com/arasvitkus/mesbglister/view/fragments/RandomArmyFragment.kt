package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.FragmentRandomArmyBinding
import com.arasvitkus.mesbglister.viewmodel.NotificationsViewModel
import com.arasvitkus.mesbglister.viewmodel.RandomArmyViewModel

class RandomArmyFragment : Fragment() {

    private var mBinding: FragmentRandomArmyBinding? = null

    private lateinit var mRandomArmyViewModel: RandomArmyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRandomArmyBinding.inflate(inflater, container, false)
        return mBinding!!.root//return the UI setup in fragment random army xml
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomArmyViewModel = ViewModelProvider(this).get(RandomArmyViewModel::class.java)

        mRandomArmyViewModel.getRandomArmyFromAPI()

        randomArmyViewModelObserver()

    }
    private fun randomArmyViewModelObserver(){
        mRandomArmyViewModel.randomArmyResponse.observe(viewLifecycleOwner,
            { randomArmyResponse -> randomArmyResponse?.let{

                Log.i("Random Army Response", "$randomArmyResponse.quotes[0]")

            }}
        )

        mRandomArmyViewModel.randomArmyLoadingError.observe(viewLifecycleOwner,
            {
               dataError ->
               dataError?.let {
                   Log.e("Random Army API Error", "$dataError")
               }
            }
            )

        mRandomArmyViewModel.loadRandomArmy.observe(viewLifecycleOwner,
            {
                loadRandomArmy ->
                loadRandomArmy?.let{
                    Log.i("Random Army Loading", "$loadRandomArmy")
                }
            }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}