package com.arasvitkus.mesbglister.view.fragments

import android.app.AlertDialog
import android.app.Dialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.databinding.DialogCustomListBinding
import com.arasvitkus.mesbglister.databinding.FragmentAllArmiesBinding
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import com.arasvitkus.mesbglister.utils.Constants
import com.arasvitkus.mesbglister.view.activities.AddUpdateListActivity
import com.arasvitkus.mesbglister.view.activities.MainActivity
import com.arasvitkus.mesbglister.view.adapters.CustomListItemAdapter
import com.arasvitkus.mesbglister.view.adapters.MesbgListerAdapter
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory

class AllArmiesFragment : Fragment() {

    private lateinit var mBinding : FragmentAllArmiesBinding

    private lateinit var mMesbgListerAdapter: MesbgListerAdapter

    private lateinit var mCustomListDialog: Dialog

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
        mMesbgListerAdapter = MesbgListerAdapter(this@AllArmiesFragment)

        mBinding.rvArmiesList.adapter = mMesbgListerAdapter

        mMesbgListerViewModel.allArmiesList.observe(viewLifecycleOwner) {
            armies ->
                armies.let{
                    if(it.isNotEmpty()){
                        mBinding.rvArmiesList.visibility = View.VISIBLE
                        mBinding.tvNoArmiesAddedYet.visibility = View.GONE

                        mMesbgListerAdapter.armiesList(it) // it is the list of armies from observer
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

    private fun filterArmiesListDialog(){
        mCustomListDialog = Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvDialogCustomListTitle.text = resources.getString(R.string.title_select_item_to_filter)
        val armyTypes = Constants.armyTypes()
        armyTypes.add(0, Constants.ALL_ITEMS)
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())

        //Using this@AllArmiesFragment emphasizes to use this particular fragment
        val adapter = CustomListItemAdapter(requireActivity(), this@AllArmiesFragment, armyTypes, Constants.FILTER_SELECTION)

        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    // Override the onCreateOptionMenu and onOptionsItemSelected methods and launch the AddUpdateListActivity on selection.
    // START
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_armies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add_army -> {
                startActivity(Intent(requireActivity(), AddUpdateListActivity::class.java))
                return true
            }

            R.id.action_filter_armies ->{
                filterArmiesListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun filterSelection(filterItemSelection: String){
        mCustomListDialog.dismiss()

        Log.i("Filter Selection", filterItemSelection)

        if(filterItemSelection == Constants.ALL_ITEMS){
                mMesbgListerViewModel.allArmiesList.observe(viewLifecycleOwner) {
                        armies ->
                    armies.let{
                        if(it.isNotEmpty()){
                            mBinding.rvArmiesList.visibility = View.VISIBLE
                            mBinding.tvNoArmiesAddedYet.visibility = View.GONE

                            mMesbgListerAdapter.armiesList(it) // it is the list of armies from observer
                        }else{
                            mBinding.rvArmiesList.visibility = View.GONE
                            mBinding.tvNoArmiesAddedYet.visibility = View.VISIBLE
                        }
                    }
            }
        } else {
            mMesbgListerViewModel.getFilteredList(filterItemSelection).observe(viewLifecycleOwner){
                armies ->
                armies.let {
                    if(it.isNotEmpty()){
                        mBinding.rvArmiesList.visibility = View.VISIBLE
                        mBinding.tvNoArmiesAddedYet.visibility = View.GONE

                        //it is the filtered list
                        mMesbgListerAdapter.armiesList(it)
                    } else{
                        mBinding.rvArmiesList.visibility = View.GONE
                        mBinding.tvNoArmiesAddedYet.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}