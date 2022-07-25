package com.arasvitkus.mesbglister.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.FragmentArmyDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.IOException
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ArmyDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArmyDetailsFragment : Fragment() {

    private var mBinding : FragmentArmyDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    //This will always be called before fun onViewCreated method
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentArmyDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ArmyDetailsFragmentArgs by navArgs()
        args.let{
            try{
                Glide.with(requireActivity())
                    .load(it.armyDetails.image)
                    .centerCrop()
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("TAG", "Error loading image", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.let {
                                Palette.from(resource!!.toBitmap()).generate(){
                                        palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb ?: 0 // Take vibrantSwatch rgb if it exists, if not, take zero
                                    mBinding!!.rlArmyDetailMain.setBackgroundColor(intColor)
                                 }

                            }
                            return false
                        }

                    })
                    .into(mBinding!!.ivArmyImage)
            }catch (e: IOException){
                e.printStackTrace()
            }

            mBinding!!.tvTitle.text = it.armyDetails.title
            mBinding!!.tvType.text = it.armyDetails.type.capitalize(Locale.ROOT)
            mBinding!!.tvFaction.text = it.armyDetails.faction
            mBinding!!.tvList.text = it.armyDetails.list
            mBinding!!.tvPoints.text = it.armyDetails.armyPoints
            mBinding!!.tvNotes.text = it.armyDetails.armyNotes
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }


}