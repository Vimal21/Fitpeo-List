package com.fitpeo.mvvmhilt.photo.ui

import android.os.Bundle
import com.fitpeo.mvvmhilt.R
import com.fitpeo.mvvmhilt.base.BaseActivity
import com.fitpeo.mvvmhilt.databinding.ActivityPhotosBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Photo list activity
 *
 * @constructor Create Photo list activity
 */
@AndroidEntryPoint
class PhotoListActivity : BaseActivity() {
    private lateinit var binding: ActivityPhotosBinding

    /**
     * Called when the activity is starting. This is where most
     * initialization should go: calling setContentView(int) to inflate the activity's UI
     *
     * @param savedInstanceState  the get bundle state while restore the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragmentInActivity(PhotoListFragment.newInstance(), R.id.frame_container)
    }
}