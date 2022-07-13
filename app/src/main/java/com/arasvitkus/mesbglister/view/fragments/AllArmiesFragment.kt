package com.arasvitkus.mesbglister.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.view.activities.AddUpdateListActivity
import com.arasvitkus.mesbglister.viewmodel.HomeViewModel

class AllArmiesFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    // TODO Step 8: Override the onCreate function and enable setHasOptionMenu to add the action menu to Fragment.
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
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_armies, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    // TODO Step 9: Override the onCreateOptionMenu and onOptionsItemSelected methods and launch the AddUpdateDishActivity on selection.
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