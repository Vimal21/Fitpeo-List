package com.fitpeo.mvvmhilt.photoDetail.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitpeo.mvvmhilt.base.BaseFragment
import com.fitpeo.mvvmhilt.config.Constants
import com.fitpeo.mvvmhilt.databinding.FragmentPhotoDetailBinding
import com.fitpeo.mvvmhilt.photo.model.Photo
import com.google.gson.Gson

class PhotoDetailFragment : BaseFragment() {

    private lateinit var fragmentPhotoDetailBinding: FragmentPhotoDetailBinding
    private var photoDetail : Photo ? = null

    /**
     * It creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater it inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return the view for fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPhotoDetailBinding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return fragmentPhotoDetailBinding.root
    }

    /**
     * Called immediately after onCreateView has returned,
     *  but before any saved state has been restored in to the view.
     *
     * @param view the view that created on the create view function
     * @param savedInstanceState   If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as PhotoDetailActivity).setHeaderPhotoImageView(photoDetail?.url)
        fragmentPhotoDetailBinding.textTitle.text = photoDetail?.title
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        photoDetail = Gson().fromJson(requireActivity().intent.getStringExtra(Constants.USER_SELECTED_DETAIL), Photo::class.java)
    }

    companion object {
        fun newInstance() = PhotoDetailFragment()
    }
}