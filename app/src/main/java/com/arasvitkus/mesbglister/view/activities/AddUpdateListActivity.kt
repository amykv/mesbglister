package com.arasvitkus.mesbglister.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arasvitkus.mesbglister.R
import com.arasvitkus.mesbglister.databinding.ActivityAddUpdateListBinding

//Taking care of adding and updating new army list
class AddUpdateListActivity : AppCompatActivity() {

    //Allows whole class to use view binging. Allows to setup action bar
    private lateinit var mBinding: ActivityAddUpdateListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding should be activity that is inflated
        mBinding = ActivityAddUpdateListBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        setupActionBar()
    }

    private fun setupActionBar(){
        //Assigning the toolbar
        setSupportActionBar(mBinding.toolbarAddArmyActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddArmyActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}