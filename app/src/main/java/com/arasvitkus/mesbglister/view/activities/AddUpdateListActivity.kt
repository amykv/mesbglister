package com.arasvitkus.mesbglister.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.ActivityAddUpdateListBinding
import com.arasvitkus.mesbglister.databinding.DialogCustomImageSelectionBinding

//Taking care of adding and updating new army list
class AddUpdateListActivity : AppCompatActivity(), View.OnClickListener {

    //Allows whole class to use view binging. Allows to setup action bar
    private lateinit var mBinding: ActivityAddUpdateListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding should be activity that is inflated
        mBinding = ActivityAddUpdateListBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        setupActionBar()

        //Assign onclicklistener to iv army image
        mBinding.ivAddArmyImage.setOnClickListener (this)
    }

    private fun setupActionBar(){
        //Assigning the toolbar
        setSupportActionBar(mBinding.toolbarAddArmyActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddArmyActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_army_image ->{
                    customImageSelectionDialog()
                    return
                }
            }
        }
    }

    private fun customImageSelectionDialog(){
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding = DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Toast.makeText(this, "Camera Clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Toast.makeText(this, "Gallery Clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}