package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.viewmodel.DashboardViewModel

class FavoriteArmiesFragment : Fragment() {

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
}