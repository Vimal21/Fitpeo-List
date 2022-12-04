package com.fitpeo.mvvmhilt.base.network.callback

import com.fitpeo.mvvmhilt.base.network.model.ErrorModel


/**
 * Network callback which holds the API result
 *
 * @param T
 * @constructor Create Network callback
 */
interface NetworkCallback<T> {
    /**
     * On complete called when the response is success
     *
     * @param result
     */
    fun onComplete(result: T?)

    /**
     * On error called when error occurred
     *
     * @param responseCode Response Code
     * @param errorResponse Error response object
     */
    fun onError(responseCode: Int, errorResponse: ErrorModel)

    /**
     * On exception called when server error or parse error
     *
     * @param t Throwable
     */
    fun onException(t: Throwable?)
}