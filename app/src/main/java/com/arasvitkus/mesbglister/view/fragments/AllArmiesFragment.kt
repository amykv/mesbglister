package com.arasvitkus.mesbglister.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.databinding.FragmentAllArmiesBinding
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import com.arasvitkus.mesbglister.view.activities.AddUpdateListActivity
import com.arasvitkus.mesbglister.view.activities.MainActivity
import com.arasvitkus.mesbglister.view.adapters.MesbgListerAdapter
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory

class AllArmiesFragment : Fragment() {

    private lateinit var mBinding : FragmentAllArmiesBinding

    private val mMesbgListerViewModel: MesbgListerViewModel by viewModels{
        MesbgListerViewModelFactory((requireActivity().application as MesbgListerApplication).repository)
    }

    // Override the onCreate function and enable setHasOptionMenu to add the action menu to Fragment.
    // START
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    // END

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllArmiesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvArmiesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        val mesbgListerAdapter = MesbgListerAdapter(this@AllArmiesFragment)

        mBinding.rvArmiesList.adapter = mesbgListerAdapter

        mMesbgListerViewModel.allArmiesList.observe(viewLifecycleOwner) {
            armies ->
                armies.let{
                    if(it.isNotEmpty()){
                        mBinding.rvArmiesList.visibility = View.VISIBLE
                        mBinding.tvNoArmiesAddedYet.visibility = View.GONE

                        mesbgListerAdapter.armiesList(it) // it is the list of armies from observer
                    }else{
                        mBinding.rvArmiesList.visibility = View.GONE
                        mBinding.tvNoArmiesAddedYet.visibility = View.VISIBLE
                    }
                }
        }
    }

    //Function to navigate to the action created in mobile_navigation.xml
    fun armyDetails(mesbgLister: MesbgLister ){
        findNavController().navigate(AllArmiesFragmentDirections.actionAllArmiesToArmyDetails(
            mesbgLister
        ))

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    fun deleteArmy(army: MesbgLister){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.title_delete_army))
        builder.setMessage(resources.getString(R.string.msg_delete_army_dialog, army.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)//built into android
        builder.setPositiveButton(resources.getString(R.string.lbl_yes)){ dialogInterface, _ ->
            mMesbgListerViewModel.delete(army)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.lbl_no)){ dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    // Override the onCreateOptionMenu and onOptionsItemSelected methods and launch the AddUpdateDishActivity on selection.
    // START
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_armies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add_army -> {
                startActivity(Intent(requireActivity(), AddUpdateListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    // END
}