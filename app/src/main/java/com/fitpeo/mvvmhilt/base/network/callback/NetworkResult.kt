package com.fitpeo.mvvmhilt.base.network.callback

import com.fitpeo.mvvmhilt.base.network.model.ErrorModel

/**
 * Result
 *
 * @param T the data
 * @constructor Create Result
 */
sealed class Result<out T : Any> {
    /**
     * Success callback
     *
     * @param T the data
     * @property value the response
     * @constructor Create Ok constructor
     */
    class Ok<out T : Any>(
        val value: T?
    ) : Result<T>() {
        override fun toString() = "Result.Ok{value=$value}"
    }

    /**
     * Error callback
     *
     * @property responseCode
     * @property errorModel
     * @property exception
     * @constructor Create Error
     */
    class Error(
        val responseCode: Int,
        val errorModel: ErrorModel,
        private val exception: Throwable? = null,
    ) : Result<Nothing>() {
        override fun toString() = "Result.Error{exception=$exception}"
    }

}
