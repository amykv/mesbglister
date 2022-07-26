package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.viewmodel.DashboardViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory

class FavoriteArmiesFragment : Fragment() {

    private val mFavoriteArmyViewModel : MesbgListerViewModel by viewModels {
        MesbgListerViewModelFactory((requireActivity().application as MesbgListerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite_armies, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "Favorite Armies Fragment"
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavoriteArmyViewModel.favoriteArmies.observe(viewLifecycleOwner) {
            armies ->
            armies.let{
                if(it.isNotEmpty()){
                    for(army in it){
                        Log.i("Favorite Army", "${army.id} :: ${army.title}")
                    }
                }else{
                    Log.i("List of Favorite Armies", "is empty.")
                }
            }
        }
    }
}