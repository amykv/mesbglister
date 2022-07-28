package com.arasvitkus.mesbglister.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.FragmentRandomArmyBinding
import com.arasvitkus.mesbglister.viewmodel.NotificationsViewModel

class RandomArmyFragment : Fragment() {

    private var mBinding: FragmentRandomArmyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRandomArmyBinding.inflate(inflater, container, false)
        return mBinding!!.root//return the UI setup in fragment random army xml
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}