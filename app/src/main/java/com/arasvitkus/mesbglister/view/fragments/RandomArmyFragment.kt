package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.viewmodel.NotificationsViewModel

class RandomArmyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_random_army, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "Random Army Fragment"
        return root
    }
}