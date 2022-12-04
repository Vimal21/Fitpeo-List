package com.fitpeo.mvvmhilt.base.network

import android.util.Log
import com.fitpeo.mvvmhilt.base.network.callback.Result
import com.fitpeo.mvvmhilt.base.network.model.ErrorModel
import com.fitpeo.mvvmhilt.config.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Suspend extension that allows suspend [Call] inside coroutine.
 *
 * @return sealed class [Result] object that can be
 *         casted to [Result.Ok] (success) or [Result.Error] (HTTP error)
 *         and [Result.Exception] (other errors)
 */
const val TAG = "CallAwait"

/**
 * Await result
 *
 * @param T
 * @return
 */
suspend fun <T : Any> Call<T>.awaitResult(): Result<T> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                continuation.resumeWith(runCatching {
                    try {
                        if (response.isSuccessful) {
                            Result.Ok(response.body())
                        } else {
                            try {
                                val errorResponse = Gson().fromJson(
                                    response.errorBody()?.string()
                                        ?: "{}", ErrorModel::class.java
                                )
                                Result.Error(response.code(), errorResponse)
                            } catch (e: Exception) {
                                Log.e(TAG, "onResponse: ", e)
                                Result.Error(
                                    422,
                                    ErrorModel(message = Constants.FAILED_TO_PARSE)
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Result.Error(422, ErrorModel(), e)
                    }
                })
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                continuation.resume(
                    Result.Error(500, ErrorModel(), t)
                )
            }
        })

        registerOnCompletion(continuation)
    }
}

/**
 * Registration after the completion of the retrofit call
 */
private fun Call<*>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (ex: Throwable) {
            //Ignore cancel exception
        }
    }
}