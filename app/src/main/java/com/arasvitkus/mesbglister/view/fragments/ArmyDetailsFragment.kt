package com.arasvitkus.mesbglister.view.fragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.databinding.FragmentArmyDetailsBinding
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import com.arasvitkus.mesbglister.utils.Constants
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory
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

    //Create global variable for Army Details and assign the args to it
    private var mMesbgListerDetails: MesbgLister? = null

    private var mBinding : FragmentArmyDetailsBinding? = null

    /**
     * To create the ViewModel used in the viewModels delegate, passing in an instance of MesbgListerViewModelFactory.
     * This is constructed based on the repository retrieved from the MesbgListerApplication.
     */
    private val mFavArmyViewModel: MesbgListerViewModel by viewModels {
        MesbgListerViewModelFactory(((requireActivity().application) as MesbgListerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set the setHasOptionsMenu to true
        setHasOptionsMenu(true)
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            //Handle Item click action and share the army list details with others
            //Not using army image yet
            R.id.action_share_army -> {
                val type = "text/plain"
                val subject = "Checkout this army list"
                var extraText = ""
                val shareWith = "Share with"

                mMesbgListerDetails?.let {
                    /* var image = ""
                    if(it.imageSource == Constants.ARMY_IMAGE_SOURCE_LOCAL){
                        image = ""
                    }*/

                    /*var armyList = ""
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        armyList = Html.fromHtml(
                            it.list,
                            Html.FROM_HTML_MODE_COMPACT
                        ).toString()
                    } else {
                        @Suppress("DEPRECATION")
                        armyList = Html.fromHtml(it.list).toString()
                    }*/

                    extraText =
                        //"$image \n" +
                                "\n Title:  ${it.title} \n\n Type: ${it.type} \n\n Faction: ${it.faction}" +
                                "\n\n List: \n ${it.list} \n\n Total Points: \n ${it.armyPoints}" +
                                "\n\n Notes: ${it.armyNotes}"
                }


                val intent = Intent(Intent.ACTION_SEND)
                intent.type = type
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, extraText)
                startActivity(Intent.createChooser(intent, shareWith))

                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    //This will always be called before fun onViewCreated method
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentArmyDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ArmyDetailsFragmentArgs by navArgs()

        //Initialize the MesbgLister global variable
        mMesbgListerDetails = args.armyDetails

        args.let{
            try{
                // Load the army image in the ImageView.
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
                            //Log exception
                            Log.e("TAG", "Error loading image", e)
                            return false // important to return false so the error placeholder can be placed
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //resource.let {
                                Palette.from(resource!!.toBitmap())
                                    .generate { palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb ?: 0 // Take vibrantSwatch rgb if it exists, if not, take zero
                                    mBinding!!.rlArmyDetailMain.setBackgroundColor(intColor)
                                 }

                            //}
                            return false
                        }

                    })
                    .into(mBinding!!.ivArmyImage)
            }catch (e: IOException){
                e.printStackTrace()
            }

            mBinding!!.tvTitle.text = it.armyDetails.title
            //mBinding!!.tvType.text = it.armyDetails.type.capitalize(Locale.ROOT)
            mBinding!!.tvType.text = it.armyDetails.type.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            mBinding!!.tvFaction.text = it.armyDetails.faction
            mBinding!!.tvList.text = it.armyDetails.list
            mBinding!!.tvPoints.text = it.armyDetails.armyPoints
            mBinding!!.tvNotes.text = it.armyDetails.armyNotes

            if(args.armyDetails.favoriteArmy){
                mBinding!!.ivFavoriteArmy.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                ))
            }else{
                mBinding!!.ivFavoriteArmy.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                ))
            }
        }

        mBinding!!.ivFavoriteArmy.setOnClickListener {
            args.armyDetails.favoriteArmy = !args.armyDetails.favoriteArmy

            mFavArmyViewModel.update(args.armyDetails)

            if(args.armyDetails.favoriteArmy){
                mBinding!!.ivFavoriteArmy.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                ))
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_added_to_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                mBinding!!.ivFavoriteArmy.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                ))
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_removed_from_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }


}