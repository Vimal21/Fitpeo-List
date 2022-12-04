package com.fitpeo.mvvmhilt.photo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitpeo.mvvmhilt.base.BaseFragment
import com.fitpeo.mvvmhilt.base.adapter.OnItemClickListener
import com.fitpeo.mvvmhilt.config.Constants
import com.fitpeo.mvvmhilt.photo.adapter.PhotoAdapter
import com.fitpeo.mvvmhilt.photo.model.Photo
import com.fitpeo.mvvmhilt.photo.viewModel.PhotoListViewModel
import com.fitpeo.mvvmhilt.databinding.FragmentPhotoBinding
import com.fitpeo.mvvmhilt.photoDetail.ui.PhotoDetailActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

/**
 * Photo list fragment
 *
 * @constructor Create Photo list fragment
 */
@AndroidEntryPoint
class PhotoListFragment : BaseFragment(), OnItemClickListener {
    private lateinit var fragmentPhotoListBinding: FragmentPhotoBinding
    private lateinit var adapter: PhotoAdapter

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
        fragmentPhotoListBinding = FragmentPhotoBinding.inflate(inflater, container, false).apply {
            photoListViewModel =
                (activity as AppCompatActivity).obtainViewModel(PhotoListViewModel::class.java)
                    .apply {
                        errorMessage.observe(viewLifecycleOwner) {
                            displayError(it)
                        }
                    }
        }
        return fragmentPhotoListBinding.root
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
        fragmentPhotoListBinding.lifecycleOwner = this.viewLifecycleOwner
        setupRecyclerView()
        fragmentPhotoListBinding.photoListViewModel?.getPhotos()

        fragmentPhotoListBinding.swipeRefreshLayout.setOnRefreshListener {
            fragmentPhotoListBinding.photoListViewModel?.getPhotos()
        }
    }

    /**
     *
     * Setup the recyclerview with needed adapter items
     */
    private fun setupRecyclerView() {
        adapter = PhotoAdapter()
        adapter.setItemListener(this)
        fragmentPhotoListBinding.photoRecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        fragmentPhotoListBinding.photoRecyclerview.adapter = adapter
    }

    /**
     * OnClick callback from the blog adapter class
     *
     * @param item  the selected item by the user
     */
    override fun onClick(item: Any) {
        val photo = item as Photo
        Toast.makeText(requireContext(), photo.title, Toast.LENGTH_SHORT).show()
        val intent = Intent(requireActivity(), PhotoDetailActivity::class.java)
        intent.putExtra(Constants.USER_SELECTED_DETAIL, Gson().toJson(photo))
        startActivity(intent)
    }

    companion object {
        fun newInstance() = PhotoListFragment()
    }
}