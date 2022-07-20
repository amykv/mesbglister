package com.arasvitkus.mesbglister.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.ActivityAddUpdateListBinding
import com.arasvitkus.mesbglister.databinding.DialogCustomImageSelectionBinding
import com.karumi.dexter.Dexter
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.arasvitkus.mesbglister.application.MesbgListerApplication
import com.arasvitkus.mesbglister.databinding.DialogCustomListBinding
import com.arasvitkus.mesbglister.model.entities.MesbgLister
import com.arasvitkus.mesbglister.utils.Constants
import com.arasvitkus.mesbglister.view.adapters.CustomListItemAdapter
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModel
import com.arasvitkus.mesbglister.viewmodel.MesbgListerViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

//Taking care of adding and updating new army list
class AddUpdateListActivity : AppCompatActivity(), View.OnClickListener {

    //Allows whole class to use view binging. Allows to setup action bar
    private lateinit var mBinding: ActivityAddUpdateListBinding
    private lateinit var resultLauncherCamera: ActivityResultLauncher<Intent>
    private lateinit var galleryImageResultLauncher: ActivityResultLauncher<Intent>

    private var mImagePath: String = ""
    private lateinit var mCustomListDialog: Dialog

    private val mMesbgListerViewModel : MesbgListerViewModel by viewModels {
        MesbgListerViewModelFactory((application as MesbgListerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding should be activity that is inflated
        mBinding = ActivityAddUpdateListBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        setupActionBar()

        //Assign onclicklistener to iv army image
        mBinding.ivAddArmyImage.setOnClickListener (this)

        mBinding.etType.setOnClickListener(this)
        mBinding.etFaction.setOnClickListener(this)
        mBinding.etPoints.setOnClickListener(this)

        mBinding.btnAddArmy.setOnClickListener(this)

        onActivityResult()
    }

    private fun setupActionBar(){
        //Assigning the toolbar
        setSupportActionBar(mBinding.toolbarAddArmyActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddArmyActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun selectedListItem(item: String, selection: String){
        when(selection){
            Constants.ARMY_TYPE ->{
                mCustomListDialog.dismiss()
                mBinding.etType.setText(item)
            }
            Constants.ARMY_FACTION ->{
                mCustomListDialog.dismiss()
                mBinding.etFaction.setText(item)
            }
            Constants.ARMY_POINTS ->{
                mCustomListDialog.dismiss()
                mBinding.etPoints.setText(item)
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_army_image ->{
                    customImageSelectionDialog()
                    return
                }
                R.id.et_type -> {
                    customItemsListDialog(resources.getString(R.string.title_select_army_type), Constants.armyTypes(), Constants.ARMY_TYPE)
                    return
                }
                R.id.et_faction -> {
                    customItemsListDialog(resources.getString(R.string.title_select_army_faction), Constants.armyFactions(), Constants.ARMY_FACTION)
                    return
                }
                R.id.et_points -> {
                    customItemsListDialog(resources.getString(R.string.title_select_army_points), Constants.armyPoints(), Constants.ARMY_POINTS)
                    return
                }

                R.id.btn_add_army -> {
                    //Trimming empty spaces
                    val title = mBinding.etTitle.text.toString().trim { it <= ' '} //lambda expression to get rid of empty spaces
                    val type = mBinding.etType.text.toString().trim { it <= ' '}
                    val faction = mBinding.etFaction.text.toString().trim { it <= ' '}
                    val list = mBinding.etList.text.toString().trim { it <= ' '}
                    val points = mBinding.etPoints.text.toString().trim { it <= ' '}
                    val notes = mBinding.etNotes.text.toString().trim { it <= ' '}

                    when {
                        TextUtils.isEmpty(mImagePath) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_select_army_image), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(title) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_enter_army_title), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(type) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_select_army_type), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(faction) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_select_army_faction), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(list) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_enter_army_list), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(points) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_select_army_points), Toast.LENGTH_SHORT).show()
                        }

                        TextUtils.isEmpty(notes) -> {
                            Toast.makeText(this@AddUpdateListActivity,
                                resources.getString(R.string.err_msg_enter_army_notes), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            val mesbgListerDetails: MesbgLister = MesbgLister(
                                mImagePath,
                                Constants.ARMY_IMAGE_SOURCE_LOCAL,
                                title,
                                type,
                                faction,
                                list,
                                points,
                                notes,
                                false
                            )
                            mMesbgListerViewModel.insert(mesbgListerDetails)
                            Toast.makeText(this@AddUpdateListActivity, "You've added army details successfully.",
                                Toast.LENGTH_SHORT).show()
                            Log.e("Insertion", "Success")
                            finish()
                        }
                    }
                }

            }
        }
    }

    private fun onActivityResult(){
        resultLauncherCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data

                    data?.extras?.let {
                        val thumbnail: Bitmap =
                            data.extras!!.get("data") as Bitmap // Bitmap from camera
                        //mBinding.ivArmyImage.setImageBitmap(thumbnail) // Set to the imageView.
                        //Glide makes it look a little better, essentially like the code above commented out.
                        Glide.with(this).load(thumbnail).centerCrop().into(mBinding.ivArmyImage)

                        mImagePath = saveImageToInternalStorage(thumbnail)
                        Log.i("ImagePath", mImagePath)

                        // Replace the add icon with edit icon once the image is selected.
                        mBinding.ivAddArmyImage.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@AddUpdateListActivity,
                                R.drawable.ic_vector_edit
                            )
                        )
                    }
                }else if (result.resultCode == Activity.RESULT_CANCELED) {
                    Log.e("Cancelled", "Cancelled")
                }
            }

        //GALLERY section
        galleryImageResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    data?.let {
                        val selectedPhotoUri = data.data

                        //mBinding.ivArmyImage.setImageURI(selectedPhotoUri)
                        Glide.with(this).load(selectedPhotoUri).centerCrop().diskCacheStrategy(
                            DiskCacheStrategy.ALL).listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("Tag", "Error loading image", e)
                                return false //Error placeholder can be placed
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap: Bitmap = resource.toBitmap()
                                    mImagePath = saveImageToInternalStorage(bitmap)
                                    Log.i("ImagePath", mImagePath)
                                }
                                return false
                            }


                        }).into(mBinding.ivArmyImage)

                        mBinding.ivAddArmyImage.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@AddUpdateListActivity,
                                R.drawable.ic_vector_edit
                            )
                        )
                    }
                }else if (result.resultCode == Activity.RESULT_CANCELED) {
                    Log.e("Cancelled", "Cancelled")
                }
            }
    }

    //Function for selecting custom image from Camera or Gallery
    private fun customImageSelectionDialog(){
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding = DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        //Camera selection
        //Implementing Dexter library to get permissions.
        binding.tvCamera.setOnClickListener {
            Dexter.withContext(this@AddUpdateListActivity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            resultLauncherCamera.launch(intent)
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {

                        showRationalDialogForPermissions()

                    }
                }).onSameThread()
                .check()
            dialog.dismiss()
        }
        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this@AddUpdateListActivity)
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        galleryImageResultLauncher.launch(galleryIntent)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@AddUpdateListActivity,
                            "You have denied the storage permission to select image.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }

                }).onSameThread()
                .check()
            dialog.dismiss()
        }
        dialog.show()
    }

    //Function to dialog for setting permissions if off
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ -> //lambda
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    //Image storage function. Get image as bitmap, return a String
    private fun saveImageToInternalStorage(bitmap: Bitmap):String {
        val wrapper = ContextWrapper(applicationContext) // Where bitmap is assigned to

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        return file.absolutePath
    }

    //Function for binding and selecting Items dialog
    private fun customItemsListDialog(title: String, itemsList: List<String>, selection: String){
        mCustomListDialog = Dialog(this)
        val binding : DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        //custom list dialog should use complete xml file dialog_custom_list.xl
        mCustomListDialog.setContentView(binding.root)
        binding.tvDialogCustomListTitle.text = title
        binding.rvList.layoutManager = LinearLayoutManager(this)

        val adapter = CustomListItemAdapter(this, itemsList, selection)
        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }

    companion object{
        //private const val CAMERA = 1
        //private const val GALLERY = 2
        //Folder where images will be stored
        private const val IMAGE_DIRECTORY = "MESBGListerImages"
    }
}