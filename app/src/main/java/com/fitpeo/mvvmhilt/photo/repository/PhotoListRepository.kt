package com.fitpeo.mvvmhilt.photo.repository

import com.fitpeo.mvvmhilt.base.network.awaitResult
import com.fitpeo.mvvmhilt.base.network.callback.NetworkCallback
import com.fitpeo.mvvmhilt.base.network.callback.Result
import com.fitpeo.mvvmhilt.photo.model.Photo
import com.fitpeo.mvvmhilt.photo.service.PhotoListApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Photo list repository
 *
 * @property photoListApi
 * @constructor Create photo list repository
 */
class PhotoListRepository @Inject constructor(
    private val photoListApi: PhotoListApi
) {
    val completableJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completableJob)
    private val foregroundScope = CoroutineScope(Dispatchers.Main)


    /**
     * Get photo list
     *
     * @param pageNumber the page number to display
     * @param pageSize the number of items in each page
     * @param callback the data handling callback to the viewModel
     */
    fun getPhotoList(pageNumber : Int, pageSize : Int, callback: NetworkCallback<List<Photo>>) {
        backgroundScope.launch {
            when (val result: Result<List<Photo>> = photoListApi.get(pageNumber, pageSize).awaitResult()) {

                /**Successful Network Request*/
                is Result.Ok -> foregroundScope.launch { callback.onComplete(result.value) }

                /**Error on the Network Result*/
                is Result.Error -> foregroundScope.launch {
                    callback.onError(result.responseCode, result.errorModel)
                }
            }
        }
    }
}