package com.arasvitkus.mesbglister.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.arasvitkus.mesbglister.databinding.ItemCustomListBinding
import com.arasvitkus.mesbglister.view.activities.AddUpdateListActivity
import com.arasvitkus.mesbglister.view.fragments.AllArmiesFragment

class CustomListItemAdapter(private val activity: Activity, private val fragment: Fragment?, private val listItems: List<String>, private val selection: String)
    : RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>() {

        class ViewHolder(view: ItemCustomListBinding): RecyclerView.ViewHolder(view.root){
            val tvText = view.tvText
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCustomListBinding = ItemCustomListBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.tvText.text = item

        holder.itemView.setOnClickListener {
            if(activity is AddUpdateListActivity){
                activity.selectedListItem(item, selection)
            }
            if(fragment is AllArmiesFragment){
                fragment.filterSelection(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}