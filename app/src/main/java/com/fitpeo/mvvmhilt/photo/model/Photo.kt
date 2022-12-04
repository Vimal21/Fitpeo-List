package com.fitpeo.mvvmhilt.photo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Blog
 *
 * @property id
 * @property albumId
 * @property title
 * @property url
 * @property thumbnailUrl
 * @constructor Create Photo
 */
data class Photo(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("albumId")
    @Expose
    var albumId: Int,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("url")
    @Expose
    var url: String,
    @SerializedName("thumbnailUrl")
    @Expose
    var thumbnailUrl: String
)
