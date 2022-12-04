package com.fitpeo.mvvmhilt.base.network.model

import com.google.gson.annotations.SerializedName

/**
 * Error model
 *
 * @property error
 * @property message
 * @constructor Create Error model
 */
data class ErrorModel(
    @SerializedName("error")
    val error: Error = Error("Something went wrong, Please try again"),
    @SerializedName("message")
    val message: String = "Something went wrong, Please try again"
) {
    /**
     * Error
     *
     * @property message
     * @constructor Create Error
     */
    data class Error(
        @SerializedName("message")
        val message: String
    )
}
