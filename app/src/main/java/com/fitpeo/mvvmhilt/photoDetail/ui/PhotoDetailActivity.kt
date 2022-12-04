package com.fitpeo.mvvmhilt.photoDetail.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import com.fitpeo.mvvmhilt.R
import com.fitpeo.mvvmhilt.base.BaseActivity
import com.fitpeo.mvvmhilt.databinding.ActivityPhotoDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

/**
 * Photo detail activity
 *
 * @constructor Create Photo detail activity
 */
@AndroidEntryPoint
class PhotoDetailActivity : BaseActivity() {
    private lateinit var photoDetailBinding: ActivityPhotoDetailBinding

    /**
     * Called when the activity is starting. This is where most
     * initialization should go: calling setContentView(int) to inflate the activity's UI
     *
     * @param savedInstanceState  the get bundle state while restore the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        photoDetailBinding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        val view = photoDetailBinding.root
        setContentView(view)
        setSupportActionBar(photoDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        replaceFragmentInActivity(PhotoDetailFragment.newInstance(), R.id.frame_container)
        photoDetailBinding.collapsingToolbar.isTitleEnabled=false
    }

    /**
     * Set the dynamic image as photo view in header view
     *
     * @param url image url selected by the user
     */
    fun setHeaderPhotoImageView(url : String?) {
        if(url == null)
            return
        Picasso.get().load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(photoDetailBinding.expandedImage)
    }

    /**
     * Performs an optional default action.
     * For the case of an action provider placed in a menu item not shown as an action this method is invoked if previous callbacks for processing menu selection has handled the event.
     * The default implementation does not perform any action and returns false.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}