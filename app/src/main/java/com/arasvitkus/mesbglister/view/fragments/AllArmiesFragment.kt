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

    //Global variable for MesbgListerAdapterClass
    private lateinit var mMesbgListerAdapter: MesbgListerAdapter

    //Global variable for Filter List Dialog
    private lateinit var mCustomListDialog: Dialog

    /**
     * To create the ViewModel used in the viewModels delegate, passing in an instance of MesbgListerViewModelFactory.
     * This is constructed based on the repository retrieved from the MesbgListerApplication.
     */
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
    ): View {
        mBinding = FragmentAllArmiesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set the LayoutManager that this RecyclerView will use
        mBinding.rvArmiesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        //Adapter class is initialized and list is passed in the param.
        mMesbgListerAdapter = MesbgListerAdapter(this@AllArmiesFragment)
        //adapter instance is set to the recyclerview to inflate the items
        mBinding.rvArmiesList.adapter = mMesbgListerAdapter

        /**
         * Add an observer on the LiveData returned by getAllArmiesList.
         * The onChanged() method fires when the observed data changes and the activity is in the foreground.
         */
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

    //Function to navigate to the action created in mobile_navigation.xml - Army Details Fragment
    fun armyDetails(mesbgLister: MesbgLister ){
        findNavController().navigate(AllArmiesFragmentDirections.actionAllArmiesToArmyDetails(
            mesbgLister
        ))

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    /**
     * Method is used to show the Alert Dialog while deleting the army details.
     *
     * @param army - Army details to delete.
     */
    fun deleteArmy(army: MesbgLister){
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.title_delete_army))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.msg_delete_army_dialog, army.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)//built into android
        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.lbl_yes)){ dialogInterface, _ ->
            mMesbgListerViewModel.delete(army)
            dialogInterface.dismiss()
        }
        //performing negative action
        builder.setNegativeButton(resources.getString(R.string.lbl_no)){ dialogInterface, _ -> // the _ was which before
            dialogInterface.dismiss()
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) //Not allow user to cancel after clicking on remaining screen area
        alertDialog.show() //Show the dialog to UI
    }

    /**
     * A function to launch the custom filter army type dialog.
     */
    private fun filterArmiesListDialog(){
        mCustomListDialog = Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mCustomListDialog.setContentView(binding.root)

        binding.tvDialogCustomListTitle.text = resources.getString(R.string.title_select_item_to_filter)
        val armyTypes = Constants.armyTypes()
        armyTypes.add(0, Constants.ALL_ITEMS)
        // Set the LayoutManager that this RecyclerView will use.
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())

        // Adapter class is initialized and list is passed in the param.
        //Using this@AllArmiesFragment emphasizes to use this particular fragment
        val adapter = CustomListItemAdapter(requireActivity(), this@AllArmiesFragment, armyTypes, Constants.FILTER_SELECTION)

        // adapter instance is set to the recyclerview to inflate the items.
        binding.rvList.adapter = adapter
        //Start the dialog and display it on screen.
        mCustomListDialog.show()
    }

    private fun displaySettings(){
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.title_select_setting_option))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.msg_privacy_policy))
        //builder.setIcon(android.R.drawable.ic_dialog_alert)//built into android
        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.lbl_dismiss)){ dialogInterface, _ ->
            //mMesbgListerViewModel.delete(army)
            dialogInterface.dismiss()
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) //Not allow user to cancel after clicking on remaining screen area
        alertDialog.show() //Show the dialog to UI

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

            R.id.action_open_settings ->{
                displaySettings()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * A function to get the filter item selection and get the list from database accordingly.
     *
     * @param filterItemSelection
     */
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