package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.databinding.FragmentFavoriteArmiesBinding
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import com.arasvitkus.mesbglister.view.activities.MainActivity
import com.arasvitkus.mesbglister.view.adapters.MesbgListerAdapter
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory

class FavoriteArmiesFragment : Fragment() {

    //Create instance of ViewBinding
    private var mBinding: FragmentFavoriteArmiesBinding? = null

    /**
     * To create the ViewModel used in the viewModels delegate, passing in an instance of MesbgListerViewModelFactory.
     * This is constructed based on the repository retrieved from the MesbgListerApplication.
     */
    private val mFavoriteArmyViewModel : MesbgListerViewModel by viewModels {
        MesbgListerViewModelFactory((requireActivity().application as MesbgListerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Initialize mBinding
        mBinding = FragmentFavoriteArmiesBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Add an observer on the LiveData returned by getFavoriteArmiesList.
         * The onChanged() method fires when the observed data changes and the activity is in the foreground.
         */
        mFavoriteArmyViewModel.favoriteArmies.observe(viewLifecycleOwner) {
            armies ->
            armies.let{
                // Display the list of favorite armies using RecyclerView.
                // Will not create a separate adapter class, will use the same that was created for AllArmies.

                // Set the LayoutManager that this RecyclerView will use.
                mBinding!!.rvFavoriteArmiesList.layoutManager =
                    GridLayoutManager(requireActivity(), 2)
                // Adapter class is initialized and list is passed in the param.
                val adapter = MesbgListerAdapter(this@FavoriteArmiesFragment)
                // Adapter instance is set to the recyclerview to inflate the items.
                mBinding!!.rvFavoriteArmiesList.adapter = adapter

                if (it.isNotEmpty()) {
                    mBinding!!.rvFavoriteArmiesList.visibility = View.VISIBLE
                    mBinding!!.tvNoFavoriteArmiesAvailable.visibility = View.GONE

                    adapter.armiesList(it)
                } else {
                    mBinding!!.rvFavoriteArmiesList.visibility = View.GONE
                    mBinding!!.tvNoFavoriteArmiesAvailable.visibility = View.VISIBLE
                }
                // END
            }
        }
    }

    //mesbgLister is the object, MesbgLister is the class
    fun armyDetails(mesbgLister: MesbgLister) {
        findNavController().navigate(FavoriteArmiesFragmentDirections
            .actionFavoriteArmiesToArmyDetails(mesbgLister))

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null //Destroy it in case it exists so it won't create future issues
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)!!.showBottomNavigationView()
        }
    }
}