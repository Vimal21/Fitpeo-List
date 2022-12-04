package com.fitpeo.mvvmhilt.photo.viewModel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fitpeo.mvvmhilt.base.network.callback.NetworkCallback
import com.fitpeo.mvvmhilt.base.network.model.ErrorModel
import com.fitpeo.mvvmhilt.photo.adapter.PhotoAdapter
import com.fitpeo.mvvmhilt.photo.model.Photo
import com.fitpeo.mvvmhilt.photo.repository.PhotoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Photo list view model
 *
 * @property photoListRepository
 * @constructor Create Photo list view model
 */
@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val photoListRepository: PhotoListRepository
) : ViewModel() {

    /**
     * while get photo list showing the loading to the user
     */
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    /**
     * show error message to the user while get blog list
     */
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    /**
     * Blog list received from the remote server
     */
    private val _photoList: MutableLiveData<List<Photo>> = MutableLiveData()
    val photoList: LiveData<List<Photo>>
        get() = _photoList

    /**
     * Get Photos
     *
     */
    fun getPhotos() {
        _dataLoading.value = true
        photoListRepository.getPhotoList(1, 100, object : NetworkCallback<List<Photo>> {
            override fun onComplete(result: List<Photo>?) {
                _dataLoading.value = false
                if (result != null) {
                    _photoList.value = result ?: arrayListOf()
                } else {
                    _photoList.value = arrayListOf()
                }
            }

            override fun onError(responseCode: Int, errorResponse: ErrorModel) {
                _dataLoading.value = false
                processError(errorModel = errorResponse)
            }

            override fun onException(t: Throwable?) {
                _dataLoading.value = false
                processError(throwable = t)
            }
        })
    }

    /**
     * process the error received from getPhotos() function and pass the proper
     * error message to the user
     *
     * @param errorModel  the error model receives from the server
     * @param throwable   error throwable while get the blog list data
     */
    fun processError(errorModel: ErrorModel? = null, throwable: Throwable? = null) {
        if (errorModel != null) {
            _errorMessage.value = errorModel.message
        } else {
            _errorMessage.value = throwable?.localizedMessage
        }
    }

    /**
     * cleared the coroutines job while viewModel
     * on cleared state
     *
     */
    override fun onCleared() {
        super.onCleared()
        photoListRepository.completableJob.cancel()
    }

    object Attributes {
        /**
         * Binding adapter for the swipe refresh layout for refresh
         */
        @JvmStatic
        @BindingAdapter("setRefresh")
        fun loadImage(swipeRefresh: SwipeRefreshLayout, isRefreshing: Boolean) {
            swipeRefresh.isRefreshing = isRefreshing
        }

        /**
         * Binding adapter for the recyclerview with list of data
         */
        @JvmStatic
        @BindingAdapter("photoList")
        fun loadBlogList(recyclerView: RecyclerView, photoList: List<Photo>?) {
            with(recyclerView.adapter as PhotoAdapter) {
                if (photoList != null && photoList.isNotEmpty())
                    setItems(photoList)
            }
        }
    }
}